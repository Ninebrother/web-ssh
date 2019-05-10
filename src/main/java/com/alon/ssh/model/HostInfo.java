package com.alon.ssh.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2019/3/21 20:20
 */
@Data
@Builder
public class HostInfo {

    private Long id;

    private String name;

    private String hostAddress;

    private Integer portNumber;

}
