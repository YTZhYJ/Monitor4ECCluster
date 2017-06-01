package com.unicom.ambari.info;
/**
 * ʹ��Gson��ȡAmbari�Ĵ��̵���ϸ��Ϣ
 * 
 * @author ZhYJ
 *
 */
public class DiskInfo {
	/**
	 * {
        "available" : "5882492",
        "used" : "1952456",
        "percent" : "25%",
        "size" : "8254240",
        "type" : "ext4",
        "mountpoint" : "/"
      },
	 */
	private String available;
	private String used;
	private String percent;
	private String size;
	private String type;
	private String mountpoint;
	
	@Override
	public String toString(){
		String contents ="����ʹ�ó�������ֵ���������ֱ�Ϊ��<br>"
				+"available:"+available+";<br>"
				+"used:"+used+";<br>"
				+"percent:"+percent+";<br>"
				+"size:"+size+";<br>"
				+"type:"+type+";<br>"
				+"mountpoint:"+mountpoint+";<br><br>";
		return contents;
	}
	
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMountpoint() {
		return mountpoint;
	}
	public void setMountpoint(String mountpoint) {
		this.mountpoint = mountpoint;
	}
	
}
