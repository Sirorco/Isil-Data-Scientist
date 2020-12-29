/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocol;

import java.util.Hashtable;

/**
 *
 * @author Thomas
 */
public class RequestBigDataResult extends BaseRequest {
    
    //For Reg-corr
    public static String  REGCORR_DATE = "REGCORR_DATE";
    public static String  REGCORR_GLOBAL_TITRE = "CAH_GLOBAL_TITRE"; // Titre globale du graphique REGCORR
    public static String  REGCORR_FORMULA = "REGCORR_FORMULA";  //Formule
    public static String  REGCORR_PLOT_ONE = "REGCORR_PLOT_ONE";    //Ensemble de petits graphiques
    public static String  REGCORR_PLOT_ONE_TEXT = "REGCORR_PLOT_ONE_TEXT";  //Descrition des petits graphiques
    public static String  REGCORR_GLOBAL_TEXT = "REGCORR_GLOBAL_TEXT";  //Conclusion Reg-Corr
    
    //For Anova2
    public static String  ANOVA2_DATE = "ANOVA2_DATE";
    public static String  ANOVA2_GLOBAL_TITRE = "CAH_GLOBAL_TITRE"; // Titre globale du graphique ANOVA2
    public static String  ANOVA2_PLOT_ONE = "ANOVA2_PLOT_ONE";  //Graph qui montre les pics pour les villes les plus populaires
    public static String  ANOVA2_PLOT_ONE_TEXT = "ANOVA2_PLOT_ONE_TEXT";    //Description du graphique
    public static String  ANOVA2_GLOBAL_TEXT = "ANOVA2_GLOBAL_TEXT"; //Conclusion Anova2
    
    //For ACM
    public static String  ACM_DATE = "ACM_DATE";
    public static String  ACM_GLOBAL_TITRE = "CAH_GLOBAL_TITRE"; // Titre globale du graphique ACM
    public static String  ACM_PLOT_ONE = "ACM_PLOT_ONE";    //Graphique qui monter les routes commerciales particulières
    public static String  ACM_PLOT_ONE_TEXT = "ACM_PLOT_ONE_TEXT";  //Description du graphique
    public static String  ACM_GLOBAL_TEXT = "ACM_GLOBAL_TEXT";  //Conclusion ACM
    
    //For CAH
    public static String  CAH_DATE = "CAH_DATE";
    public static String  CAH_GLOBAL_TITRE = "CAH_GLOBAL_TITRE"; // Titre globale du graphique CAH
    public static String  CAH_PLOT_ONE = "CAH_PLOT_ONE";    //Graphique qui montre les différentes classes
    public static String  CAH_PLOT_ONE_TEXT = "CAH_PLOT_ONE_TEXT";  //Description du graphique
    public static String  CAH_PLOT_TWO = "CAH_PLOT_TWO";    //Graphique des bénéfices mensuels
    public static String  CAH_PLOT_TWO_TEXT = "CAH_PLOT_TWO_TEXT";  //Description du graphique
    public static String  CAH_GLOBAL_TEXT = "CAH_GLOBAL_TEXT";  //Conclusion ACH
    
    private Hashtable data;

    /**
     * @return the data
     */
    public Hashtable getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Hashtable data) {
        this.data = data;
    }
    
    public Object getValue (String key)
    {
        if (data!=null)
        {
            return data.get(key);
        }
        
        return null;
    }
}
