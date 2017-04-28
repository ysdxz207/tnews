/**
 * 
 */
package com.puyixiaowo.tnews.manager;

import org.apache.shiro.codec.Base64;

/**
 * @author huangfeihong
 * @date 2017年2月1日 下午3:44:32
 */
public class TestShiroCipherKey {
	public static void main(String[] args) {
		
		System.out.println(Base64.encodeToString("lsywomenjiehunba".getBytes()));
		System.out.println(Base64.decodeToString("bHN5d29tZW5qaWVodW5iYQ=="));
	}
}
