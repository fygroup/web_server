
package com.jeeplus.modules.networkinterface.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.networkinterface.entity.NetInterfaceInOutRate;
import com.jeeplus.modules.networkinterface.entity.NetworkInterface;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 网络接口MAPPER接口
 * @author le
 * @version 2017-11-08
 */
@MyBatisMapper
public interface NetworkInterfaceMapper extends BaseMapper<NetworkInterface> {
    void saveList(@Param(value = "list") List<NetworkInterface> list);
    void delByResourceId (@Param(value = "id") String id );
    void saveRateList(@Param(value = "list") List<NetInterfaceInOutRate> list);
    NetInterfaceInOutRate findTopRate(@Param(value = "networkInterfaceId") String networkInterfaceId,@Param(value = "type") String type);
    List<NetInterfaceInOutRate> findRateList(@Param(value = "networkInterfaceId") String networkInterfaceId,@Param(value = "type") String type);
    List<NetworkInterface> findListBySort(@Param(value = "resourceId") String  resourceId);
    List<NetworkInterface> findListByResourceTypeCode(@Param(value = "code") String  code);
    List<NetworkInterface> onlineNetInterface(@Param(value = "resourceId") String  resourceId);
    List<NetworkInterface> list();

    List<NetInterfaceInOutRate> findRateListByResourceIdType(@Param(value = "type") String  type,@Param(value = "resourceId") String  resourceId,@Param(value = "num") String  num);

    NetInterfaceInOutRate getTopUsed(@Param(value = "resourceId") String resourceId, @Param(value = "beginDate") Date beginDate , @Param(value = "endDate") Date endDate );
}