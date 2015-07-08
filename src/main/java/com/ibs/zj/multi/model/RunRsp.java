package com.ibs.zj.multi.model;

public class RunRsp {
	/**
	 * 操作是否成功
	 */
	private volatile boolean isSuceess = false;

	/**
	 * 当前运行的id信息
	 */
	private int id;

	/**
	 * 组id信息
	 */
	private int groupId;
	/**
	 * 当前任务执行的sql
	 */
	private String sql;

	/**
	 * 当前任务所要推送的局方库表名
	 */
	private String tableName;

	/**
	 * 任务执行的时间
	 */
	private String startTime;

	public boolean isSuceess() {
		return isSuceess;
	}

	public void setSuceess(boolean isSuceess) {
		this.isSuceess = isSuceess;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public String toString() {
		return "RunRsp [isSuceess=" + isSuceess + ", id=" + id + ", groupId="
				+ groupId + "]";
	}
}
