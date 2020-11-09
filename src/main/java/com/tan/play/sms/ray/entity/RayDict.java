package com.tan.play.sms.ray.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "ray_sendsms.ray_dict")
public class RayDict {
  @Id
  @Column(name = "DICT_ID")
  private String dictId;

  @Column(name = "DICT_TYPE")
  private String dictType;

  @Column(name = "DICT_CODE")
  private String dictCode;

  @Column(name = "DICT_DESC")
  private String dictDesc;

  @Column(name = "DICT_NAME")
  private String dictName;

  @Column(name = "DICT_SORT")
  private Integer dictSort;

  @Column(name = "DICT_ADDTIME")
  private String dictAddtime;

  /** @return DICT_ID */
  public String getDictId() {
    return dictId;
  }

  /** @param dictId */
  public void setDictId(String dictId) {
    this.dictId = dictId;
  }

  /** @return DICT_TYPE */
  public String getDictType() {
    return dictType;
  }

  /** @param dictType */
  public void setDictType(String dictType) {
    this.dictType = dictType;
  }

  /** @return DICT_CODE */
  public String getDictCode() {
    return dictCode;
  }

  /** @param dictCode */
  public void setDictCode(String dictCode) {
    this.dictCode = dictCode;
  }

  /** @return DICT_DESC */
  public String getDictDesc() {
    return dictDesc;
  }

  /** @param dictDesc */
  public void setDictDesc(String dictDesc) {
    this.dictDesc = dictDesc;
  }

  /** @return DICT_NAME */
  public String getDictName() {
    return dictName;
  }

  /** @param dictName */
  public void setDictName(String dictName) {
    this.dictName = dictName;
  }

  /** @return DICT_SORT */
  public Integer getDictSort() {
    return dictSort;
  }

  /** @param dictSort */
  public void setDictSort(Integer dictSort) {
    this.dictSort = dictSort;
  }

  /** @return DICT_ADDTIME */
  public String getDictAddtime() {
    return dictAddtime;
  }

  /** @param dictAddtime */
  public void setDictAddtime(String dictAddtime) {
    this.dictAddtime = dictAddtime;
  }
}
