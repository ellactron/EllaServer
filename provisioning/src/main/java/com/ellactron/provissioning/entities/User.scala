package com.ellactron.provissioning.entities

import java.util.Date
import javax.persistence._

import com.fasterxml.jackson.annotation.JsonIgnore

/**
  * Created by ji.wang on 2017-07-02.
  */
/*@SqlResultSetMapping(
  name="UserResultMapping",
  classes = Array(new ConstructorResult(targetClass = classOf[User],
  columns = Array(
    new ColumnResult(name = "id"),
    new ColumnResult(name = "username"),
    new ColumnResult(name = "phoneNumber"),
    new ColumnResult(name = "email"),
    new ColumnResult(name = "registerDate"),
    new ColumnResult(name = "lastActiviteDate")
  ))))*/
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
  def setPhoneNumber(phoneNumber:String) = this.phoneNumber = phoneNumber
  def getPhoneNumber = phoneNumber

  @Column(name="email")
  private var email:String = _
  def setEmail(email:String) = this.email = email
  def getEmail = email

  @Column(name="register_datetime", nullable = false)
  private var registerDate:Date = _
  def setRegisterDate(date: Date) = this.registerDate=date
  def getRegisterDate = registerDate

  @Column(name="last_activite_datetime")
  private var lastActiviteDate:Date = _
  def setLastActiviteDate(date: Date) = this.lastActiviteDate=date
  def getLastActiviteDate = lastActiviteDate

  def this(username:String, password: String) {
    this()
    setUsername(username)
    setPassword(password)
  }

  def this(id:Integer, username:String, phoneNumber:String, email:String,registerDate:Date, lastActiviteDate:Date) {
    this()
    setId(id)
    setUsername(username)
    this.phoneNumber=phoneNumber
    this.email=email
    this.registerDate=registerDate
    this.lastActiviteDate=lastActiviteDate
  }
}
