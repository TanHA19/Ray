package com.tan.play.sms.ray.service.impl;

import com.tan.play.sms.ray.entity.RaySysAnnouncement;
import com.tan.play.sms.ray.mapper.RaySysAnnouncementMapper;
import com.tan.play.sms.ray.service.RaySysAnnouncementService;
import org.springframework.stereotype.Service;
import utils.MyMapper;

/** @Author tan */
@Service
public class RaySysAnnouncementServiceImpl<T> extends BaseServiceImpl<RaySysAnnouncement>
    implements RaySysAnnouncementService<RaySysAnnouncement> {

  private final RaySysAnnouncementMapper raySysAnnouncementMapper;

  public RaySysAnnouncementServiceImpl(RaySysAnnouncementMapper raySysAnnouncementMapper) {
    this.raySysAnnouncementMapper = raySysAnnouncementMapper;
  }

  @Override
  public MyMapper<RaySysAnnouncement> getDao() {
    return raySysAnnouncementMapper;
  }
}
