/**
 * 
 */
package com.zyf.util;

import java.util.Date;

/**
 * 商家
 * 
 * @author yuhui.tang 2013-10-27 下午4:24:19
 */
public class Merchant {
	private String merchantid;
	private Date createtime;
	private int a;
	private Integer b;
	private Long c;
	private long d;

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public Integer getB() {
		return b;
	}

	public void setB(Integer b) {
		this.b = b;
	}

	public Long getC() {
		return c;
	}

	public void setC(Long c) {
		this.c = c;
	}

	public long getD() {
		return d;
	}

	public void setD(long d) {
		this.d = d;
	}

	public String getMerchantid() {
		return merchantid;
	}

	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}
