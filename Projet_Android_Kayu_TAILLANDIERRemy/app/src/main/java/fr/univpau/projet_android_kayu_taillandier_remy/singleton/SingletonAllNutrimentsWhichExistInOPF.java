package fr.univpau.projet_android_kayu_taillandier_remy.singleton;


import android.app.Application;

import java.util.HashMap;



public class SingletonAllNutrimentsWhichExistInOPF extends Application {

    private static HashMap<String,String> typeNutriments = new HashMap<String, String>();

    @Override
    public void onCreate() {
        super.onCreate();

        typeNutriments.put("energy-kj", "Énergie (kJ)");
        typeNutriments.put("energy-kcal", "Énergie (kcal)");
        typeNutriments.put("energy", "Énergie");
        typeNutriments.put("energy-from-fat", "Énergie provenant des graisses");
        typeNutriments.put("fat", "Matières grasses / Lipides");
        typeNutriments.put("saturated-fat", "Acides gras saturés");
        typeNutriments.put("butyric-acid", "Acide butyrique (4:0)");
        typeNutriments.put("caproic-acid", "Acide caproïque (6:0)");
        typeNutriments.put("caprylic-acid", "Acide caproïque (8:0)");
        typeNutriments.put("capric-acid", "Acide caprique (10:0)");
        typeNutriments.put("lauric-acid", "Acide laurique (12:0)");
        typeNutriments.put("myristic-acid", "Acide myristique (14:0)");
        typeNutriments.put("palmitic-acid", "Acide palmitique (16:0)");
        typeNutriments.put("stearic-acid", "Acide stéarique (18:0)");
        typeNutriments.put("arachidic-acid", "Acide arachidique / acide eicosanoïque (20:0)");
        typeNutriments.put("behenic-acid", "Acide béhénique (22:0)");
        typeNutriments.put("lignoceric-acid", "Acide lignocérique (24:0)");
        typeNutriments.put("cerotic-acid", "Acide cérotique (26:0)");
        typeNutriments.put("montanic-acid", "Acide montanique (28:0)");
        typeNutriments.put("melissic-acid", "Acide mélissique (30:0)");
        typeNutriments.put("monounsaturated-fat", "Acides gras monoinsaturés");
        typeNutriments.put("polyunsaturated-fat", "Acides gras polyinsaturés");
        typeNutriments.put("omega-3-fat", "Acides gras Oméga 3");
        typeNutriments.put("alpha-linolenic-acid", "Acide alpha-linolénique / ALA (18:3 n-3)");
        typeNutriments.put("eicosapentaenoic-acid", "Acide eicosapentaénoïque / EPA (20:5 n-3)");
        typeNutriments.put("docosahexaenoic-acid", "Acide docosahexaénoïque / DHA (22:6 n-3)");
        typeNutriments.put("omega-6-fat", "Acides gras Oméga 6");
        typeNutriments.put("linoleic-acid", "Acide linoléique / LA (18:2 n-6)");
        typeNutriments.put("arachidonic-acid", "Acide arachidonique / AA / ARA (20:4 n-6)");
        typeNutriments.put("gamma-linolenic-acid", "Acide gamma-linolénique / GLA (18:3 n-6)");
        typeNutriments.put("dihomo-gamma-linolenic-acid", "Acide dihomo-gamma-linolénique / DGLA (20:3 n-6)");
        typeNutriments.put("omega-9-fat", "Acides gras Oméga 9");
        typeNutriments.put("oleic-acid", "Acide oléique (18:1 n-9)");
        typeNutriments.put("elaidic-acid", "Acide élaïdique (18:1 n-9)");
        typeNutriments.put("gondoic-acid", "Acide gadoléique (20:1 n-9)");
        typeNutriments.put("mead-acid", "Acide de Mead (20:3 n-9)");
        typeNutriments.put("erucic-acid", "Acide érucique (22:1 n-9)");
        typeNutriments.put("nervonic-acid", "Acide nervonique (24:1 n-9)");
        typeNutriments.put("trans-fat", "Acides gras trans");
        typeNutriments.put("cholesterol", "Cholestérol");
        typeNutriments.put("carbohydrates", "Glucides");
        typeNutriments.put("sugars", "Sucres");
        typeNutriments.put("sucrose", "Saccharose");
        typeNutriments.put("glucose", "Glucose");
        typeNutriments.put("fructose", "Fructose");
        typeNutriments.put("lactose", "Lactose");
        typeNutriments.put("maltose", "Maltose");
        typeNutriments.put("maltodextrins", "Maltodextrines");
        typeNutriments.put("starch", "Amidon");
        typeNutriments.put("polyols", "Polyols");
        typeNutriments.put("fiber", "Fibres alimentaires");
        typeNutriments.put("proteins", "Protéines");
        typeNutriments.put("casein", "Caséine");
        typeNutriments.put("serum-proteins", "Protéines sériques");
        typeNutriments.put("nucleotides", "Nucléotides");
        typeNutriments.put("salt", "Sel");
        typeNutriments.put("sodium", "Sodium");
        typeNutriments.put("alcohol", "Alcool");
        typeNutriments.put("vitamin-a", "Vitamine A (rétinol)");
        typeNutriments.put("beta-carotene", "Bêta carotène");
        typeNutriments.put("vitamin-d", "Vitamine D / D3 (cholécalciférol)");
        typeNutriments.put("vitamin-e", "Vitamine E (tocophérol)");
        typeNutriments.put("vitamin-k", "Vitamine K");
        typeNutriments.put("vitamin-c", "Vitamine C (acide ascorbique)");
        typeNutriments.put("vitamin-b1", "Vitamine B1 (Thiamine)");
        typeNutriments.put("vitamin-b2", "Vitamine B2 (Riboflavine)");
        typeNutriments.put("vitamin-pp", "Vitamine B3 / Vitamine PP (Niacine)");
        typeNutriments.put("vitamin-b6", "Vitamine B6 (Pyridoxine)");
        typeNutriments.put("vitamin-b9", "Vitamine B9 (Acide folique)");
        typeNutriments.put("folates", "Folates (folates totaux)");
        typeNutriments.put("vitamin-b12", "Vitamine B12 (cobalamine)");
        typeNutriments.put("biotin", "Biotine (Vitamine B8 / B7 / H)");
        typeNutriments.put("pantothenic-acid", "Acide pantothénique (Vitamine B5)");
        typeNutriments.put("silica", "Silice");
        typeNutriments.put("bicarbonate", "Bicarbonate");
        typeNutriments.put("potassium", "Potassium");
        typeNutriments.put("chloride", "Chlorure");
        typeNutriments.put("calcium", "Calcium");
        typeNutriments.put("phosphorus", "Phosphore");
        typeNutriments.put("iron", "Fer");
        typeNutriments.put("magnesium", "Magnésium");
        typeNutriments.put("zinc", "Zinc");
        typeNutriments.put("copper", "Cuivre");
        typeNutriments.put("manganese", "Manganèse");
        typeNutriments.put("fluoride", "Fluorure");
        typeNutriments.put("selenium", "Sélénium");
        typeNutriments.put("chromium", "Chrome");
        typeNutriments.put("molybdenum", "Molybdène");
        typeNutriments.put("iodine", "Iode");
        typeNutriments.put("caffeine", "Caféine / Théine");
        typeNutriments.put("ph", "ph");
        typeNutriments.put("fruits-vegetables-nuts", "Fruits, légumes, noix et huiles de colza, noix et olive");
        typeNutriments.put("fruits-vegetables-nuts-dried", "Fruits, légumes et noix - séchés");
        typeNutriments.put("fruits-vegetables-nuts-estimate", "Fruits, légumes, noix et huiles de colza, noix et olive (estimation avec la liste des ingrédients)");
        typeNutriments.put("collagen-meat-protein-ratio", "Rapport collagène sur protéines de viande (maximum)");
        typeNutriments.put("cocoa", "Cacao (minimum)");
        typeNutriments.put("chlorophyl", "chlorophyl");
        typeNutriments.put("carbon-footprint", "Empreinte carbone / émissions de CO2");
        typeNutriments.put("carbon-footprint-from-meat-or-fish", "Empreinte carbone de la viande ou du poisson");
        typeNutriments.put("glycemic-index", "glycemic-index");
        typeNutriments.put("water-hardness", "Dureté de l'eau");
        typeNutriments.put("choline", "Choline");
        typeNutriments.put("phylloquinone", "Vitamine K1");
        typeNutriments.put("beta-glucan", "Bêta-glucanes");
        typeNutriments.put("inositol", "inositol");
        typeNutriments.put("carnitine", "carnitine");
    }

    public static synchronized HashMap<String, String> getInstance(){
        return typeNutriments;
    }
}
