package cn.zzuisa.base;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageRequest implements Serializable {

	private static final long serialVersionUID = -4869594085374385813L;

	// 每页显示条数 默认10
	private int size = 10;

	// 当前页 默认1
    private int current = 1;


	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		this.current = current;
	}



}
