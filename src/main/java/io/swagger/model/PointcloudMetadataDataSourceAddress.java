package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PointcloudMetadataDataSourceAddress
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-08T12:14:47.659Z[GMT]")


public class PointcloudMetadataDataSourceAddress   {
  @JsonProperty("street")
  private String street = null;

  @JsonProperty("hno")
  private String hno = null;

  @JsonProperty("zipCode")
  private String zipCode = null;

  @JsonProperty("country")
  private String country = null;

  public PointcloudMetadataDataSourceAddress street(String street) {
    this.street = street;
    return this;
  }

  /**
   * Get street
   * @return street
   **/
  @Schema(description = "")
  
    public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public PointcloudMetadataDataSourceAddress hno(String hno) {
    this.hno = hno;
    return this;
  }

  /**
   * Get hno
   * @return hno
   **/
  @Schema(description = "")
  
    public String getHno() {
    return hno;
  }

  public void setHno(String hno) {
    this.hno = hno;
  }

  public PointcloudMetadataDataSourceAddress zipCode(String zipCode) {
    this.zipCode = zipCode;
    return this;
  }

  /**
   * Get zipCode
   * @return zipCode
   **/
  @Schema(description = "")
  
    public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public PointcloudMetadataDataSourceAddress country(String country) {
    this.country = country;
    return this;
  }

  /**
   * Get country
   * @return country
   **/
  @Schema(description = "")
  
    public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PointcloudMetadataDataSourceAddress pointcloudMetadataDataSourceAddress = (PointcloudMetadataDataSourceAddress) o;
    return Objects.equals(this.street, pointcloudMetadataDataSourceAddress.street) &&
        Objects.equals(this.hno, pointcloudMetadataDataSourceAddress.hno) &&
        Objects.equals(this.zipCode, pointcloudMetadataDataSourceAddress.zipCode) &&
        Objects.equals(this.country, pointcloudMetadataDataSourceAddress.country);
  }

  @Override
  public int hashCode() {
    return Objects.hash(street, hno, zipCode, country);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PointcloudMetadataDataSourceAddress {\n");
    
    sb.append("    street: ").append(toIndentedString(street)).append("\n");
    sb.append("    hno: ").append(toIndentedString(hno)).append("\n");
    sb.append("    zipCode: ").append(toIndentedString(zipCode)).append("\n");
    sb.append("    country: ").append(toIndentedString(country)).append("\n");
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
