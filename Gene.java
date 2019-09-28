public class Gene {

    public double latitude;
    public double longitude;
    public String name;

    public Gene(double lat , double lng , String n)
    {
        this.latitude=lat;
        this.longitude=lng;
        this.name=n;
    }

    /*
    * conversion utile pour la formule de Haversine
    * */
    public double deg2rad(double deg)
    {
        return deg*(Math.PI/180D);
    }

    /* dans cette méthode j'ai utilisé la formule de Haversine pour calculer la distance entre 2 points
     * à partir des coordonnées
    */
    public double distanceBetweenTwoGenes(Gene gene)
    {
        double R = 6371;
        double dLat = deg2rad(gene.latitude - this.latitude);
        double dLng = deg2rad(gene.longitude - this.longitude);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(deg2rad(this.latitude)) * Math.cos(deg2rad(gene.latitude)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c; // la distance en KM
        return d;
    }

}
