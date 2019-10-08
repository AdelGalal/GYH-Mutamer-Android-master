package com.gama.mutamer.viewModels.prayTimes;

/**
 * Created by mustafa on 5/15/18.
 * Release the GEEK
 */
/**
 * Mathhab is used for Assr prayer calculation.
 */
public class Mathhab {
    /**
     * Assr prayer shadow ratio: use Shaa'fi mathhab (default)
     */
    public static final Mathhab SHAAFI = new Mathhab();
    /**
     * Assr prayer shadow ratio: use Hanafi mathhab
     */
    public static final Mathhab HANAFI = new Mathhab();

    private Mathhab() {

    }


}
