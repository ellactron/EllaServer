package com.ellactron.provissioning.entities

import java.util.Date
import javax.persistence._

import com.fasterxml.jackson.annotation.JsonIgnore

/**
  * Created by ji.wang on 2017-07-02.
  */
@Entity
@Table(name = "users")
class User {
  @Id
  @Column(name="user_id")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private var id: Integer = _
  def setId(value:Integer):Unit = id = value
  def getId = id

  @Column(name="account", nullable = false)
  private var username: String = _
  def setUsername(value:String):Unit = username = value
  def getUsername = username

  @JsonIgnore
  @Column(name="password")
  private var password:String = _
  def setPassword(value:String):Unit = password = value
  def getPassword = password

  @Column(name="phone_number")
  private var phoneNumber:String = _

  @Column(name="email")
  private var email:String = _

  @Column(name="register_datetime", nullable = false)
  private var registerDate:Date = _

  @Column(name="last_activite_datetime")
  private var lastActiviteDate:Date = _

  def this(username:String, password: String) {
    this()
    setUsername(username)
    setPassword(password)
  }
}
