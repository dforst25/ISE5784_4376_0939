package primitives;

public class Material {

    //----------------------------fields--------------------------

    /**
     * The diffuse reflection coefficient of the material.
     */
    public Double3 kD = Double3.ZERO;

    /**
     * The specular reflection coefficient of the material.
     */
    public Double3 kS = Double3.ZERO;

    /**
     * The transmission coefficient of the material.
     */
    public Double3 kT = Double3.ZERO;

    /**
     * The reflection coefficient of the material.
     */
    public Double3 kR = Double3.ZERO;

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
     * Sets the transmission coefficient of the material.
     *
     * @param kT The new transmission coefficient.
     * @return This material object, with the updated changes.
     */
    public Material setkT(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * Sets the reflection coefficient of the material.
     *
     * @param kR The new reflection coefficient.
     * @return This material object, with the updated changes.
     */
    public Material setkR(Double3 kR) {
        this.kR = kR;
        return this;

    }


    /**
     * Sets the transmission coefficient of the material.
     *
     * @param kT The new transmission coefficient.
     * @return This material object, with the updated changes.
     */
    public Material setkT(double kT) {
        this.kT = new Double3(kT);
        return this;

    }


    /**
     * Sets the reflection coefficient of the material.
     *
     * @param kR The new reflection coefficient.
     * @return This material object, with the updated changes.
     */
    public Material setkR(double kR) {
        this.kR = new Double3(kR);
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