package com.jk.redis.key;

import com.jk.redis.rediskey.BasePrefix;

public class MiaoshaUserKey extends BasePrefix {

	/**
	 * 此时需要设置该key在Redis中的失效时间
	 */
	public static final int TOKEN_EXPIRE = 3600*24 * 2;

	/**
	 * 因为需要设置失效时间，所以使用双参构造函数
	 */
	private MiaoshaUserKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	/**作为redis中用户的Session的key前缀**/
	public static MiaoshaUserKey token = new MiaoshaUserKey(TOKEN_EXPIRE, "tk");
	/**作为redis中用户的对象缓存的key前缀**/
	public static MiaoshaUserKey getById = new MiaoshaUserKey(0,"id");
}
