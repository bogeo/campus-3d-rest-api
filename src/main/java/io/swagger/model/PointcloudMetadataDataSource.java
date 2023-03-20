package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.PointcloudMetadataDataSourceAddress;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * contact
 */
@Schema(description = "contact")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-08T12:14:47.659Z[GMT]")


public class PointcloudMetadataDataSource   {
  @JsonProperty("institution")
  private String institution = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("address")
  private PointcloudMetadataDataSourceAddress address = null;

  @JsonProperty("mail")
  private String mail = null;

  public PointcloudMetadataDataSource institution(String institution) {
    this.institution = institution;
    return this;
  }

  /**
   * Get institution
   * @return institution
   **/
  @Schema(description = "")
  
    public String getInstitution() {
    return institution;
  }

  public void setInstitution(String institution) {
    this.institution = institution;
  }

  public PointcloudMetadataDataSource name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   **/
  @Schema(description = "")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public PointcloudMetadataDataSource address(PointcloudMetadataDataSourceAddress address) {
    this.address = address;
    return this;
  }

  /**
   * Get address
   * @return address
   **/
  @Schema(description = "")
  
    @Valid
    public PointcloudMetadataDataSourceAddress getAddress() {
    return address;
  }

  public void setAddress(PointcloudMetadataDataSourceAddress address) {
    this.address = address;
  }

  public PointcloudMetadataDataSource mail(String mail) {
    this.mail = mail;
    return this;
  }

  /**
   * Get mail
   * @return mail
   **/
  @Schema(description = "")
  
    public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PointcloudMetadataDataSource pointcloudMetadataDataSource = (PointcloudMetadataDataSource) o;
    return Objects.equals(this.institution, pointcloudMetadataDataSource.institution) &&
        Objects.equals(this.name, pointcloudMetadataDataSource.name) &&
        Objects.equals(this.address, pointcloudMetadataDataSource.address) &&
        Objects.equals(this.mail, pointcloudMetadataDataSource.mail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(institution, name, address, mail);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PointcloudMetadataDataSource {\n");
    
    sb.append("    institution: ").append(toIndentedString(institution)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    mail: ").append(toIndentedString(mail)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
