//Uri resultUri = result.getUri();
//
//        //needs to be a scale factor of 2
//
//
//
//
//        //scale here
//        try {
//        scaledBitmap = scale_image(resultUri);
//
//        if(m_image_Uri == null)
//        {
//        m_image_Uri=resultUri;
//        ImageView new_image_device =image.findViewById(R.id.image_device);
//        new_image_device.setImageBitmap(scaledBitmap);
//        add_weather_image();
//        }
//        else{
//        m_image_Uri=resultUri;
//        ImageView new_image_device =image.findViewById(R.id.image_device);
//        new_image_device.setImageBitmap(scaledBitmap);
//        update_weather_image(sunny_weather_id,m_image_Uri.toString());
//
//        }
//        } catch (Exception e) {
//        e.printStackTrace();
//        }