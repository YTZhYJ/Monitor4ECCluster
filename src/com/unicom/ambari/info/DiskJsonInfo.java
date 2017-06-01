package com.unicom.ambari.info;

import java.util.List;

/**
 * ��ȡĳ��������ʹ����Ϣ�����ر��������й��ش��̵���Ϣ
 * Host -> disk_info -> percent
 * Class: HostJsonInfo -> DiskJsonInfo -> Disk
 * 
 * @author ZhYJ
 */
public class DiskJsonInfo {
	
	/**
	 * "disk_info" : [
      {
        "available" : "5882492",
        "used" : "1952456",
        "percent" : "25%",
        "size" : "8254240",
        "type" : "ext4",
        "mountpoint" : "/"
      },
      {
        "available" : "3757056",
        "used" : "0",
        "percent" : "0%",
        "size" : "3757056",
        "type" : "tmpfs",
        "mountpoint" : "/dev/shm"
      },
      {
        "available" : "411234588",
        "used" : "203012",
        "percent" : "1%",
        "size" : "433455904",
        "type" : "ext3",
        "mountpoint" : "/grid/0"
      },
      {
        "available" : "411234588",
        "used" : "203012",
        "percent" : "1%",
        "size" : "433455904",
        "type" : "ext3",
        "mountpoint" : "/grid/1"
      }
    ],
	 */
	private List<DiskInfo> disk_info;
	
	private String state;//��Ⱥ��״̬

	public List<DiskInfo> getDisk_info() {
		return disk_info;
	}

	public void setDisk_info(List<DiskInfo> disk_info) {
		this.disk_info = disk_info;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
}
