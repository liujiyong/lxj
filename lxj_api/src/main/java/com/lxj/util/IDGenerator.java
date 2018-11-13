/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lxj.util;

import java.util.UUID;

/**
 *
 * @author admin
 */
public final class IDGenerator {

    public final static String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
