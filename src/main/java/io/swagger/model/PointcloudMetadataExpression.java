package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.model.PointcloudMetadataExpressionPointDense;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * distribution of points
 */
@Schema(description = "distribution of points")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-12-08T12:14:47.659Z[GMT]")


public class PointcloudMetadataExpression   {
  /**
   * Gets or Sets kind
   */
  public enum KindEnum {
    REGULAR("regular"),
    
    IRREGULAR("irregular");

    private String value;

    KindEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static KindEnum fromValue(String text) {
      for (KindEnum b : KindEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("kind")
  private KindEnum kind = null;

  @JsonProperty("pointDense")
  private PointcloudMetadataExpressionPointDense pointDense = null;

  public PointcloudMetadataExpression kind(KindEnum kind) {
    this.kind = kind;
    return this;
  }

  /**
   * Get kind
   * @return kind
   **/
  @Schema(description = "")
  
    public KindEnum getKind() {
    return kind;
  }

  public void setKind(KindEnum kind) {
    this.kind = kind;
  }

  public PointcloudMetadataExpression pointDense(PointcloudMetadataExpressionPointDense pointDense) {
    this.pointDense = pointDense;
    return this;
  }

  /**
   * Get pointDense
   * @return pointDense
   **/
  @Schema(description = "")
  
    @Valid
    public PointcloudMetadataExpressionPointDense getPointDense() {
    return pointDense;
  }

  public void setPointDense(PointcloudMetadataExpressionPointDense pointDense) {
    this.pointDense = pointDense;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PointcloudMetadataExpression pointcloudMetadataExpression = (PointcloudMetadataExpression) o;
    return Objects.equals(this.kind, pointcloudMetadataExpression.kind) &&
        Objects.equals(this.pointDense, pointcloudMetadataExpression.pointDense);
  }

  @Override
  public int hashCode() {
    return Objects.hash(kind, pointDense);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PointcloudMetadataExpression {\n");
    
    sb.append("    kind: ").append(toIndentedString(kind)).append("\n");
    sb.append("    pointDense: ").append(toIndentedString(pointDense)).append("\n");
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
