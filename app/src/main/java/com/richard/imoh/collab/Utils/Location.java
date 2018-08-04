package com.richard.imoh.collab.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LENOVO on 7/23/2018.
 */

public class Location {
    public ArrayList<String>defaultCity = new ArrayList<>();
    public ArrayList<String> states = new ArrayList<>();
    public List<String>abia;
    public ArrayList<String>adamawa = new ArrayList<>();
    public ArrayList<String>lagos = new ArrayList<>();

    public ArrayList<String> getAdamawa() {
        adamawa.add("Choose City");
        adamawa.add("Demsa");
        adamawa.add("Fufure");
        adamawa.add("Ganye");
        adamawa.add("Gayuk");
        adamawa.add("Gombi");
        adamawa.add("Gombi");
        adamawa.add("Grie");
        adamawa.add("Hong");
        adamawa.add("Jada");
        adamawa.add("Larmurde");
        adamawa.add("Madagali");
        adamawa.add("Maiha");
        adamawa.add("Mayo Belwa");
        adamawa.add("Michika");
        adamawa.add("Mubi North");
        adamawa.add("Mubi South");
        adamawa.add("Numan");
        adamawa.add("Shelleng");
        adamawa.add("Song");
        adamawa.add("Toungo");
        adamawa.add("Yola North");
        adamawa.add("Yola South");
        return adamawa;
    }

    public List<String> getAbia() {
        abia.add("Aba North");
        abia.add("Aba South");
        abia.add("Arochukwu");
        abia.add("Bende");
        abia.add("Ikwuano");
        abia.add("Isiala Ngwa North");
        abia.add("Isiala Ngwa South");
        abia.add("Isuikwuato");
        abia.add("Obi Ngwa");
        abia.add("Ohafia");
        abia.add("Osisioma");
        abia.add("Ugwunagbo");
        abia.add("Ukwa East");
        abia.add("Ukwa West");
        abia.add("Umuahia North");
        abia.add("Umuahia South");
        abia.add("Umu Nneochi");
        return abia;
    }

    public ArrayList<String> getStates() {
        states.add("Choose State");
        states.add("Abia");
        states.add("Abuja");
        states.add("Akwa-Ibom");
        states.add("Anambra");
        states.add("Adamawa");
        states.add("Bauchi");
        states.add("Bayelsa");
        states.add("Benue");
        states.add("Borno");
        states.add("Cross-River");
        states.add("Delta");
        states.add("Ebonyi");
        states.add("Edo");
        states.add("Ekiti");
        states.add("Enugu");
        states.add("Gombe");
        states.add("Imo");
        states.add("Jigawa");
        states.add("Kaduna");
        states.add("Kano");
        states.add("Katsina");
        states.add("Kebbi");
        states.add("Kogi");
        states.add("Kwara");
        states.add("Lagos");
        states.add("Nassarawa");
        states.add("Niger");
        states.add("Ogun");
        states.add("Ondo");
        states.add("Osun");
        states.add("Oyo");
        states.add("Plataue");
        states.add("Rivers");
        states.add("Sokoto");
        states.add("Taraba");
        states.add("Yobe");
        states.add("Zamfara");
        return states;
    }
   public ArrayList<String> getLagos(){
       lagos.add("Choose City");
        lagos.add("Abule-egba");
        lagos.add("Agege");
        lagos.add("Apapa");
        lagos.add("Badagry");
        lagos.add("Ebute Meta");
        lagos.add("Ikorodu");
        lagos.add("Epe");
        lagos.add("Festac");
        lagos.add("Ifako");
        lagos.add("Ikeja");
        lagos.add("Ikotun");
        lagos.add("Ikoyi");
        lagos.add("Munshin");
        lagos.add("Ojota");
        lagos.add("Oshodi");
        lagos.add("Shomolu");
        lagos.add("Surulere");
        lagos.add("Yaba");
        lagos.add("Berger");
        lagos.add("Ketu");
        lagos.add("Banana Island");
        lagos.add("Lekki");
        lagos.add("Ajah");

        return lagos;

    }

    public ArrayList<String> getDefaultCity() {
       defaultCity.add("Choose a State");
        return defaultCity;
    }

    public String[] countries(){
        String []country = {"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe"};
        return country;
    }



}
