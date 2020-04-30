package cn.zzuisa.base;

/**
 * 返回值
 * @author 泽朋
 *
 * @param <T>
 */
public class R<T> {

	public static final int SUCCESS = 0;
	public static final int UNLOGIN = 1;
	public static final int ERROR = 2;

	private Integer code;
	private T content;

	/**
	 * 通用错误code时调用
	 * @param content
	 * @return
	 */
	public static <Y> R<Y> error(Y content) {
		R<Y> r = new R<Y>(ERROR);
		r.setContent(content);
		return r;
	}

	public static <Y> R<Y> error(Integer code,Y content) {
		R<Y> r = new R<Y>(code);
		r.setContent(content);
		return r;
	}
	/**
	 * 未登录
	 * @param content
	 * @return
	 */
	public static <Y> R<Y> unLogin(Y content) {
		R<Y> r = new R<Y>(UNLOGIN);
		r.setContent(content);
		return r;
	}
	/**
	 * 成功时调用
	 * @param content
	 * @return
	 */
	public static <Y> R<Y> ok(Y content) {
		R<Y> r = new R<Y>(SUCCESS);
		r.setContent(content);
		return r;
	}

	/*分界线：以下为构造函数和getter、setter*/

	public R(Integer code, T content) {
		this.code = code;
		this.content = content;
	}

	public R(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

}
