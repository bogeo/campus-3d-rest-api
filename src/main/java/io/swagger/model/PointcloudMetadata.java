package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.PointcloudMetadataAccuracy;
import io.swagger.model.PointcloudMetadataDataSource;
import io.swagger.model.PointcloudMetadataExpression;
import io.swagger.model.PointcloudMetadataMeasureDate;
import io.swagger.model.PointcloudMetadataProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PointcloudMetadata
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-08T12:14:47.659Z[GMT]")


public class PointcloudMetadata   {

  private String name = null;

  public String getName(){return name;}

  public void setName(String name){this.name = name;}



  @JsonProperty("accuracy")
  private PointcloudMetadataAccuracy accuracy = null;

  @JsonProperty("expression")
  private PointcloudMetadataExpression expression = null;

  /**
   * method with which the point cloud was acquired
   */
  public enum MeasureMethodEnum {
    ALS("ALS"),
    
    TLS("TLS"),
    
    IMAGERY("imagery"),
    
    MLS("MLS"),
    
    SONAR_MULTIBEAM("Sonar / Multibeam"),
    
    OTHER("other");

    private String value;

    MeasureMethodEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static MeasureMethodEnum fromValue(String text) {
      for (MeasureMethodEnum b : MeasureMethodEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("measureMethod")
  private MeasureMethodEnum measureMethod = null;

  @JsonProperty("measureDate")
  private PointcloudMetadataMeasureDate measureDate = null;

  @JsonProperty("dataSource")
  private PointcloudMetadataDataSource dataSource = null;

  /**
   * Gets or Sets attributeFields
   */
  public enum AttributeFieldsEnum {
    RGB("RGB"),
    
    INTENSITY("Intensity"),
    
    NONE("None"),
    
    OTHER("Other");

    private String value;

    AttributeFieldsEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static AttributeFieldsEnum fromValue(String text) {
      for (AttributeFieldsEnum b : AttributeFieldsEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("attributeFields")
  @Valid
  private List<AttributeFieldsEnum> attributeFields = new ArrayList<AttributeFieldsEnum>();

  @JsonProperty("freeText")
  private String freeText = null;

  @JsonProperty("projection")
  private PointcloudMetadataProjection projection = null;

  @JsonProperty("license")
  private String license = null;

  @JsonProperty("boundary")
  @Valid
  private String boundary = null;

  @JsonProperty("raster")
  private byte[] raster = null;

  public PointcloudMetadata accuracy(PointcloudMetadataAccuracy accuracy) {
    this.accuracy = accuracy;
    return this;
  }

  /**
   * Get accuracy
   * @return accuracy
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public PointcloudMetadataAccuracy getAccuracy() {
    return accuracy;
  }

  public void setAccuracy(PointcloudMetadataAccuracy accuracy) {
    this.accuracy = accuracy;
  }

  public PointcloudMetadata expression(PointcloudMetadataExpression expression) {
    this.expression = expression;
    return this;
  }

  /**
   * Get expression
   * @return expression
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public PointcloudMetadataExpression getExpression() {
    return expression;
  }

  public void setExpression(PointcloudMetadataExpression expression) {
    this.expression = expression;
  }

  public PointcloudMetadata measureMethod(MeasureMethodEnum measureMethod) {
    this.measureMethod = measureMethod;
    return this;
  }

  /**
   * method with which the point cloud was acquired
   * @return measureMethod
   **/
  @Schema(required = true, description = "method with which the point cloud was acquired")
      @NotNull

    public MeasureMethodEnum getMeasureMethod() {
    return measureMethod;
  }

  public void setMeasureMethod(MeasureMethodEnum measureMethod) {
    this.measureMethod = measureMethod;
  }

  public PointcloudMetadata measureDate(PointcloudMetadataMeasureDate measureDate) {
    this.measureDate = measureDate;
    return this;
  }

  /**
   * Get measureDate
   * @return measureDate
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public PointcloudMetadataMeasureDate getMeasureDate() {
    return measureDate;
  }

  public void setMeasureDate(PointcloudMetadataMeasureDate measureDate) {
    this.measureDate = measureDate;
  }

  public PointcloudMetadata dataSource(PointcloudMetadataDataSource dataSource) {
    this.dataSource = dataSource;
    return this;
  }

  /**
   * Get dataSource
   * @return dataSource
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public PointcloudMetadataDataSource getDataSource() {
    return dataSource;
  }

  public void setDataSource(PointcloudMetadataDataSource dataSource) {
    this.dataSource = dataSource;
  }

  public PointcloudMetadata attributeFields(List<AttributeFieldsEnum> attributeFields) {
    this.attributeFields = attributeFields;
    return this;
  }

  public PointcloudMetadata addAttributeFieldsItem(AttributeFieldsEnum attributeFieldsItem) {
    this.attributeFields.add(attributeFieldsItem);
    return this;
  }

  /**
   * values assigned to the points in space
   * @return attributeFields
   **/
  @Schema(required = true, description = "values assigned to the points in space")
      @NotNull

    public List<AttributeFieldsEnum> getAttributeFields() {
    return attributeFields;
  }

  public void setAttributeFields(List<AttributeFieldsEnum> attributeFields) {
    this.attributeFields = attributeFields;
  }

  public PointcloudMetadata freeText(String freeText) {
    this.freeText = freeText;
    return this;
  }

  /**
   * further informations
   * @return freeText
   **/
  @Schema(required = true, description = "further informations")
      @NotNull

    public String getFreeText() {
    return freeText;
  }

  public void setFreeText(String freeText) {
    this.freeText = freeText;
  }

  public PointcloudMetadata projection(PointcloudMetadataProjection projection) {
    this.projection = projection;
    return this;
  }

  /**
   * Get projection
   * @return projection
   **/
  @Schema(required = true, description = "")
      @NotNull

    @Valid
    public PointcloudMetadataProjection getProjection() {
    return projection;
  }

  public void setProjection(PointcloudMetadataProjection projection) {
    this.projection = projection;
  }

  public PointcloudMetadata license(String license) {
    this.license = license;
    return this;
  }

  /**
   * license type
   * @return license
   **/
  @Schema(required = true, description = "license type")
      @NotNull

    public String getLicense() {
    return license;
  }

  public void setLicense(String license) {
    this.license = license;
  }

  public PointcloudMetadata boundary(String boundary) {
    this.boundary = boundary;
    return this;
  }

  /**
   * boundary of pointcloud in WGS84
   * @return boundary
   **/
  @Schema(required = true, description = "boundary of pointcloud in WGS84")
      @NotNull
    @Valid
    public String getBoundary() {
    return boundary;
  }

  public void setBoundary(String boundary) {
    this.boundary = boundary;
  }

  public PointcloudMetadata raster(byte[] raster) {
    this.raster = raster;
    return this;
  }

  /**
   * raster as base64 string
   * @return raster
   **/
  @Schema(description = "raster as base64 string")
  
    public byte[] getRaster() {
    return raster;
  }

  public void setRaster(byte[] raster) {
    this.raster = raster;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PointcloudMetadata pointcloudMetadata = (PointcloudMetadata) o;
    return Objects.equals(this.accuracy, pointcloudMetadata.accuracy) &&
        Objects.equals(this.expression, pointcloudMetadata.expression) &&
        Objects.equals(this.measureMethod, pointcloudMetadata.measureMethod) &&
        Objects.equals(this.measureDate, pointcloudMetadata.measureDate) &&
        Objects.equals(this.dataSource, pointcloudMetadata.dataSource) &&
        Objects.equals(this.attributeFields, pointcloudMetadata.attributeFields) &&
        Objects.equals(this.freeText, pointcloudMetadata.freeText) &&
        Objects.equals(this.projection, pointcloudMetadata.projection) &&
        Objects.equals(this.license, pointcloudMetadata.license) &&
        Objects.equals(this.boundary, pointcloudMetadata.boundary) &&
        Objects.equals(this.raster, pointcloudMetadata.raster);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accuracy, expression, measureMethod, measureDate, dataSource, attributeFields, freeText, projection, license, boundary, raster);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PointcloudMetadata {\n");
    
    sb.append("    accuracy: ").append(toIndentedString(accuracy)).append("\n");
    sb.append("    expression: ").append(toIndentedString(expression)).append("\n");
    sb.append("    measureMethod: ").append(toIndentedString(measureMethod)).append("\n");
    sb.append("    measureDate: ").append(toIndentedString(measureDate)).append("\n");
    sb.append("    dataSource: ").append(toIndentedString(dataSource)).append("\n");
    sb.append("    attributeFields: ").append(toIndentedString(attributeFields)).append("\n");
    sb.append("    freeText: ").append(toIndentedString(freeText)).append("\n");
    sb.append("    projection: ").append(toIndentedString(projection)).append("\n");
    sb.append("    license: ").append(toIndentedString(license)).append("\n");
    sb.append("    boundary: ").append(toIndentedString(boundary)).append("\n");
    sb.append("    raster: ").append(toIndentedString(raster)).append("\n");
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
