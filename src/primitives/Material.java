package primitives;

public class Material {

    //----------------------------fields--------------------------

    /**
     * The specular reflection coefficient of the material.
     */
    public Double3 kD = Double3.ZERO;
    public Double3 kS = Double3.ZERO;

    /**
     * The shininess exponent of the material.
     */
    public int nShininess = 0;


    //--------------------------------Setters----------------------------

    /**
     * Sets the diffuse reflection coefficient of the material.
     *
     * @param kD The new diffuse reflection coefficient.
     * @return This material object, with the updated diffuse reflection coefficient.
     */
    public Material setkD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient of the material.
     *
     * @param kD The new diffuse reflection coefficient.
     * @return This material object, with the updated diffuse reflection coefficient.
     */
    public Material setkD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Sets the specular reflection coefficient of the material.
     *
     * @param kS The new specular reflection coefficient.
     * @return This material object, with the updated specular reflection coefficient.
     */
    public Material setkS(Double3 kS) {
        this.kS = kS;
        return this;

    }

    /**
     * Sets the specular reflection coefficient of the material.
     *
     * @param kS The new specular reflection coefficient.
     * @return This material object, with the updated specular reflection coefficient.
     */
    public Material setkS(double kS) {

        this.kS = new Double3(kS);
        return this;

    }

    /**
     * Sets the shininess exponent of the material.
     *
     * @param nShininess The new shininess exponent.
     * @return This material object, with the updated shininess exponent.
     */
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;

    }


}