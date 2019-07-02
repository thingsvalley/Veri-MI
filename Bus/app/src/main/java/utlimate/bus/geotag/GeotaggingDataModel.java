package utlimate.bus.geotag;
import android.graphics.Bitmap;

/**
 * Created by Aisha on 3/19/2018.
 */

public class GeotaggingDataModel {

    /**
     * Created by Aisha on 3/17/2018.
     */


        String latitude, longitude, time, date;
        Bitmap image;

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getTime() {
            return time;
        }

        public String getDate() {
            return date;
        }

        public Bitmap getImage() {
            return image;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setImage(Bitmap image) {
            this.image = image;
        }
    }


