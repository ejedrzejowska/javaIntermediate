
package pl.sda.intermediate.weather.model; ;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Wind {

    @SerializedName("speed")
    @Expose
    public Double speed;
    @SerializedName("deg")
    @Expose
    public Double deg;

}
