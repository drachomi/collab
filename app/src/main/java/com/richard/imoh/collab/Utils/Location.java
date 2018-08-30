package com.richard.imoh.collab.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LENOVO on 7/23/2018.
 */

public class Location {
    private ArrayList<String>defaultCity = new ArrayList<>();
    private ArrayList<String> states = new ArrayList<>();
    private ArrayList<String>city = new ArrayList<>();

    public ArrayList<String>getLocation(String location){
        switch (location){
            case "States":
                return getStates();
            case "Abia":
                return getAbia();
            case "Adamawa":
                return getAdamawa();
            case "Lagos":
                return getLagos();
            case "Akwa-Ibom":
                return getAkwaIbom();
            case "Anambra":
                return getAnambra();
            case "Abuja":
                return getAbuja();
            case "Rivers":
                return getRivers();
            default:

        }

        return getDefaultCity();
    }





    private ArrayList<String> getAdamawa() {
        city.add("Choose City");
        city.add("Demsa");
        city.add("Fufure");
        city.add("Ganye");
        city.add("Gayuk");
        city.add("Gombi");
        city.add("Gombi");
        city.add("Grie");
        city.add("Hong");
        city.add("Jada");
        city.add("Larmurde");
        city.add("Madagali");
        city.add("Maiha");
        city.add("Mayo Belwa");
        city.add("Michika");
        city.add("Mubi North");
        city.add("Mubi South");
        city.add("Numan");
        city.add("Shelleng");
        city.add("Song");
        city.add("Toungo");
        city.add("Yola North");
        city.add("Yola South");
        return city;
    }

    private ArrayList<String> getAbia() {
        city.add("Aba North");
        city.add("Aba South");
        city.add("Arochukwu");
        city.add("Bende");
        city.add("Ikwuano");
        city.add("Isiala Ngwa North");
        city.add("Isiala Ngwa South");
        city.add("Isuikwuato");
        city.add("Obi Ngwa");
        city.add("Ohafia");
        city.add("Osisioma");
        city.add("Ugwunagbo");
        city.add("Ukwa East");
        city.add("Ukwa West");
        city.add("Umuahia North");
        city.add("Umuahia South");
        city.add("Umu Nneochi");
        return city;
    }

    private ArrayList<String> getStates() {
        states.add("Choose State");
        states.add("Abia");
        states.add("Abuja");
        states.add("Akwa-Ibom");
        states.add("Anambra");
        states.add("Adamawa");
        states.add("Lagos");
        states.add("Rivers");
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
        states.add("Nassarawa");
        states.add("Niger");
        states.add("Ogun");
        states.add("Ondo");
        states.add("Osun");
        states.add("Oyo");
        states.add("Plataue");
        states.add("Sokoto");
        states.add("Taraba");
        states.add("Yobe");
        states.add("Zamfara");
        return states;
    }
   private ArrayList<String> getLagos(){
       city.add("Choose City");
        city.add("Abule-egba");
        city.add("Agege");
        city.add("Apapa");
        city.add("Badagry");
        city.add("Ebute Meta");
        city.add("Ikorodu");
        city.add("Epe");
        city.add("Festac");
        city.add("Ifako");
        city.add("Ikeja");
        city.add("Ikotun");
        city.add("Ikoyi");
        city.add("Munshin");
        city.add("Ojota");
        city.add("Oshodi");
        city.add("Shomolu");
        city.add("Surulere");
        city.add("Yaba");
        city.add("Berger");
        city.add("Ketu");
        city.add("Banana Island");
        city.add("Lekki");
        city.add("Ajah");

        return city;

    }

    private ArrayList<String> getDefaultCity() {
       defaultCity.add("Choose a State");
        return defaultCity;
    }
    private ArrayList<String>getAkwaIbom(){
        city.add("Abak");
        city.add("Eastern Obolo");
        city.add("Eket");
        city.add("Esit Eket");
        city.add("Essien Udim");
        city.add("Etim Ekpo");
        city.add("Etinan");
        city.add("Ibeno");
        city.add("Ibesikpo Asutan");
        city.add("Ibiono-Ibom'");
        city.add("Ika");
        city.add("Ikono");
        city.add("Ikot Abasi");
        city.add("Ikot Ekpene");
        city.add("Ini");
        city.add("Itu");
        city.add("Mbo");
        city.add("Mkpat-Enin");
        city.add("Nsit-Atai");
        city.add("Nsit-Ibom");
        city.add("Nsit-Ubium");
        city.add("Ikot Akara");
        city.add("Okobo");
        city.add("Onna");
        city.add("Oron");
        city.add("Oruk Anam");
        city.add("Udung-Uko");
        city.add("Ukanafun");
        city.add("Uruan");
        city.add("Uyo");
        return city;
    }
    private ArrayList<String>getAnambra(){
        city.add("Aguata");
        city.add("Anambra East");
        city.add("Anambra West");
        city.add("Anaocha");
        city.add("Awka North");
        city.add("Awka South");
        city.add("Ayamelum");
        city.add("Dunukofia");
        city.add("Ekwusigo");
        city.add("Idemili North");
        city.add("Idemili South");
        city.add("Ihialla");
        city.add("Njikoka");
        city.add("Nnewi North");
        city.add("Nnewi South");
        city.add("Itu");
        city.add("Mbo");
        city.add("Mkpat-Enin");
        city.add("Nsit-Atai");
        city.add("Nsit-Ibom");
        city.add("Nsit-Ubium");
        city.add("Ogbaru");
        city.add("Onitsha");
        city.add("Orumba");
        city.add("Oyi");
        return  city;
    }
    private ArrayList<String>getAbuja(){
        city.add("Abaji");
        city.add("Bwari");
        city.add("Gwagwalada");
        city.add("Kuje");
        city.add("Kwali");
        city.add("Municipal Area Council");
        return city;
    }
    private ArrayList<String>getRivers(){
        city.add("Abua");
        city.add("Ahoada");
        city.add("Akuku");
        city.add("Andoni");
        city.add("Eleme");
        city.add("Ikwere");
        city.add("Bonny");
        city.add("Degema");
        city.add("Akuku");
        city.add("Khana");
        city.add("Akpor");
        city.add("Ogu");
        city.add("Port-Harcourt");
        return city;
    }

    private String[] countries(){
        String []country = {"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arcity", "Senegal", "Seychelles", "Sierra Leone", "Singapore", "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon", "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan, Province of China", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)", "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe"};
        return country;
    }



}
