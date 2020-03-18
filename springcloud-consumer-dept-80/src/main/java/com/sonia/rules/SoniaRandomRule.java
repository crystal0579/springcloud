/*
 *
 * Copyright 2013 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.sonia.rules;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class SoniaRandomRule extends AbstractLoadBalancerRule {

    /**
     * 设定每个机器访问5次，换下一个机器访问（假定现在有3台机器）
     * total = 0， 默认=0， 如果=5，我们就指向下一个机器
     * currentIndex = 0， 默认=0， 如果 total = 5， currentIndex + 1;
     */
    private int total = 0;//被调用的次数

    private int currentIndex = 0;//目前是谁在提供服务

//    @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "RCN_REDUNDANT_NULLCHECK_OF_NULL_VALUE")
    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        }
        Server server = null;

        while (server == null) {
            if (Thread.interrupted()) {
                return null;
            }
            List<Server> upList = lb.getReachableServers();//获得或者的服务  发现一个问题，这个list 每次获得的顺序可能会不一样
            List<Server> allList = lb.getAllServers();//获得全部的服务

            int serverCount = allList.size();//从活着的服务中随机获得一个服务实例
            if (serverCount == 0) {
                return null;
            }

            /**放弃原Random代码
            int index = chooseRandomInt(serverCount);//生成随机数
            server = upList.get(index);
*/
            /**
             * 开始写自己的代码
             * 因为uplist的顺序总是有变化，所以这样五次换一个服务器的要求还是不能满足
             * 如果非要严格，可以对uplist进行排序
             */
            //-================================================================
            if (total < 5){
                server = upList.get(currentIndex);
                total ++;
            }else{
                total = 0;
                currentIndex ++;
                if (currentIndex >= upList.size()){
                    currentIndex = 0;
                }
                server = upList.get(currentIndex);//从活着的服务中来获取指定的服务操作
            }

            //-================================================================
            if (server == null) {
                Thread.yield();
                continue;
            }

            if (server.isAlive()) {
                return (server);
            }

            // Shouldn't actually happen.. but must be transient or a bug.
            server = null;
            Thread.yield();
        }

        return server;

    }

    /**
     * 生成随机数，一个不超过 serverCount的数字
     * 可以欣赏一下
     */
    protected int chooseRandomInt(int serverCount) {
        return ThreadLocalRandom.current().nextInt(serverCount);
    }

	@Override
	public Server choose(Object key) {
		return choose(getLoadBalancer(), key);
	}

	@Override
	public void initWithNiwsConfig(IClientConfig clientConfig) {
		// TODO Auto-generated method stub
		
	}
}
