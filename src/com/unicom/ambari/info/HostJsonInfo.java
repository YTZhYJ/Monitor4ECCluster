package com.unicom.ambari.info;

/**
 * 获取某主机的主机信息
 * Host -> disk_info
 * Class: HostJson -> DiskInfo
 * @author ZhYJ
 *
 */
public class HostJsonInfo {
	/**
	 * {
  "href" : "http://10.124.0.19:8080/api/v1/clusters/Minerva/hosts/zk2.hcbss",
  "Hosts" : {
    "cluster_name" : "Minerva",
    "cpu_count" : 64,
    "disk_info" : [
      {
        "available" : "165324364",
        "device" : "/dev/sda2",
        "used" : "128584116",
        "percent" : "44%",
        "size" : "309637120",
        "type" : "ext4",
        "mountpoint" : "/"
      },
      {
        "available" : "24117248",
        "device" : "tmpfs",
        "used" : "0",
        "percent" : "0%",
        "size" : "24117248",
        "type" : "tmpfs",
        "mountpoint" : "/dev/shm"
      },
      {
        "available" : "154325",
        "device" : "/dev/sda1",
        "used" : "33736",
        "percent" : "18%",
        "size" : "198313",
        "type" : "ext4",
        "mountpoint" : "/boot"
      }
    ],
    "host_health_report" : "",
    "host_name" : "zk2.hcbss",
    "host_state" : "HEALTHY",
	 */
	
	DiskJsonInfo Hosts;

	public DiskJsonInfo getHosts() {
		return Hosts;
	}

	public void setHosts(DiskJsonInfo hosts) {
		Hosts = hosts;
	}
	
	
}
