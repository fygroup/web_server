package com.jeeplus.modules.cpu.vo;

import com.jeeplus.modules.cpu.entity.Cpu;
import com.jeeplus.modules.cpu.entity.CpuUsedRate;

import java.util.List;

public class CpuVO {

    private List<Cpu> cpus;
    private CpuUsedRate cpuUsedRate;



    public CpuVO() {
    }

    public CpuVO(List<Cpu> cpus, CpuUsedRate cpuUsedRate) {
        this.cpus = cpus;
        this.cpuUsedRate = cpuUsedRate;
    }

    public List<Cpu> getCpus() {
        return cpus;
    }

    public void setCpus(List<Cpu> cpus) {
        this.cpus = cpus;
    }

    public CpuUsedRate getCpuUsedRate() {
        return cpuUsedRate;
    }

    public void setCpuUsedRate(CpuUsedRate cpuUsedRate) {
        this.cpuUsedRate = cpuUsedRate;
    }
}
