#include <Arduino.h>

// fuzzy
#include <Fuzzy.h>
#include <FuzzyComposition.h>
#include <FuzzyIO.h>
#include <FuzzyInput.h>
#include <FuzzyOutput.h>
#include <FuzzyRule.h>
#include <FuzzyRuleAntecedent.h>
#include <FuzzyRuleConsequent.h>
#include <FuzzySet.h>

#define FUZZY_IN_EEC 1
#define FUZZY_IN_VOL 2
#define FUZZY_OUT_SIRAM 3

uint32_t t_relay_start_on = 0;
#define PIN_RELAY 9
#define APP_PORT_DEBUG Serial
// fuzzy object
Fuzzy *fuzzy_main_obj = new Fuzzy();

// input error
FuzzySet *error_kecil = new FuzzySet(0, 0, 0.1, 0.2);
FuzzySet *error_sedang = new FuzzySet(0.1, 0.2, 0.2, 0.3);
FuzzySet *error_tinggi = new FuzzySet(0.2, 0.3, 0.4, 0.4);

// input vol
FuzzySet *vol_kosong = new FuzzySet(0, 0, 5, 10);
FuzzySet *vol_medium = new FuzzySet(5, 10, 10, 15);
FuzzySet *vol_penuh = new FuzzySet(10, 15, 20, 20);

// output durasi siram
FuzzySet *siram_sebentar = new FuzzySet(0, 0, 4, 8);
FuzzySet *siram_cukup = new FuzzySet(4, 8, 8, 10);
FuzzySet *siram_lama = new FuzzySet(8, 10, 12, 12);

float fuzzy_duration_out = 0.0;

/**
 * create new fuzzy rule with and boolean
 * @method createNewFuzzyRule
 * @param  ruleId             id rule
 * @param  in1                fuzzy set 1
 * @param  in2                fuzzy set 2
 * @param  out1               fuzzt set output
 * @return                    new fuzzy rule
 */

 void APP_DEBUG_PRINT(String alog) {
  char dtx[16] = {0};
  snprintf_P(dtx, sizeof(dtx), (const char *)F("%-10u : "), millis());
  APP_PORT_DEBUG.println(String(dtx) + alog);
}

 FuzzyRule *createNewFuzzyRule(int ruleId, FuzzySet *in1, FuzzySet *in2,
                              FuzzySet *out1) {
  FuzzyRuleConsequent *fzThen = new FuzzyRuleConsequent();
  fzThen->addOutput(out1);

  FuzzyRuleAntecedent *fzIf = new FuzzyRuleAntecedent();
  fzIf->joinWithAND(in1, in2);

  return new FuzzyRule(ruleId, fzIf, fzThen);
}

/**
 * init all fuzzy input, rule, and output
 * @method fuzzyInit
 */
void fuzzyInit() {
  // FuzzyInput eec
  FuzzyInput *fz_eec = new FuzzyInput(FUZZY_IN_EEC);
  fz_eec->addFuzzySet(error_kecil);
  fz_eec->addFuzzySet(error_sedang);
  fz_eec->addFuzzySet(error_tinggi);
  fuzzy_main_obj->addFuzzyInput(fz_eec);

  // FuzzyInput vol
  FuzzyInput *fz_vol = new FuzzyInput(FUZZY_IN_VOL);
  fz_vol->addFuzzySet(vol_kosong);
  fz_vol->addFuzzySet(vol_medium);
  fz_vol->addFuzzySet(vol_penuh);
  fuzzy_main_obj->addFuzzyInput(fz_vol);

  // FuzzyOutput siram
  FuzzyOutput *fz_siram = new FuzzyOutput(FUZZY_OUT_SIRAM);
  fz_siram->addFuzzySet(siram_sebentar);
  fz_siram->addFuzzySet(siram_cukup);
  fz_siram->addFuzzySet(siram_lama);
  fuzzy_main_obj->addFuzzyOutput(fz_siram);

  // fuzzy rule
  fuzzy_main_obj->addFuzzyRule(
      createNewFuzzyRule(1, error_kecil, vol_kosong, siram_sebentar));

  fuzzy_main_obj->addFuzzyRule(
      createNewFuzzyRule(2, error_kecil, vol_medium, siram_cukup));

  fuzzy_main_obj->addFuzzyRule(
      createNewFuzzyRule(3, error_kecil, vol_penuh, siram_sebentar));

  fuzzy_main_obj->addFuzzyRule(
      createNewFuzzyRule(4, error_sedang, vol_kosong, siram_sebentar));

  fuzzy_main_obj->addFuzzyRule(
      createNewFuzzyRule(5, error_sedang, vol_medium, siram_cukup));

  fuzzy_main_obj->addFuzzyRule(
      createNewFuzzyRule(6, error_sedang, vol_penuh, siram_cukup));

  fuzzy_main_obj->addFuzzyRule(
      createNewFuzzyRule(7, error_tinggi, vol_kosong, siram_lama));

  fuzzy_main_obj->addFuzzyRule(
      createNewFuzzyRule(8, error_tinggi, vol_medium, siram_cukup));

  fuzzy_main_obj->addFuzzyRule(
      createNewFuzzyRule(9, error_tinggi, vol_penuh, siram_lama));
}

/**
 * calculate duration fuzzy
 * @method fuzzyProcessInput
 * @param  eecx             eecerature
 * @param  volx              volidity
 * @param  duration_out      output duration
 */
void fuzzyProcessInput(float eecx, float volx, float *duration_out) {
  fuzzy_main_obj->setInput(FUZZY_IN_EEC, eecx);
  fuzzy_main_obj->setInput(FUZZY_IN_VOL, volx);

  fuzzy_main_obj->fuzzify();

  *duration_out = fuzzy_main_obj->defuzzify(FUZZY_OUT_SIRAM);
}


/**
 * processing fuzzy way
 * @method processFuzzySystem
 */
void processFuzzySystem() {
          float nilaieec = 0.1 ;
          float nilaivol = 5.0;
          fuzzyProcessInput(nilaieec,
                            nilaivol, &fuzzy_duration_out);
          t_relay_start_on = 0;

          APP_DEBUG_PRINT(String("DURATION = ") + String(fuzzy_duration_out));
        }
      
/**
 * relay on or off
 * @method processRelayOnOff
 */
void processRelayOnOff() {
  if (fuzzy_duration_out > 0.0) {
    if (t_relay_start_on == 0) {
      t_relay_start_on = millis();
    }
    digitalWrite(PIN_RELAY, ((millis() - t_relay_start_on) <=
                             ((uint32_t)(fuzzy_duration_out * 1000.0))));
  } else {
    if (t_relay_start_on != 0) {
      t_relay_start_on = 0;
    }
    digitalWrite(PIN_RELAY, LOW);
  }
}

/**
 * debug fuzzy system
 * @method debugTest
 */
void debugTest() {
  float durx = 0.0;

  // set timeout for serial
  APP_PORT_DEBUG.setTimeout(30000);

  APP_DEBUG_PRINT(F("EEC = "));
  float eecx = 0.1;
  APP_DEBUG_PRINT(F("VOL = "));
  float volx = 5.0;

  if ((eecx >= 0.0) && (volx >= 0.00)) {
    fuzzyProcessInput(eecx, volx, &durx);

    APP_DEBUG_PRINT(String("EECFZ = ") + String(eecx) + String(" -- ") +
                    String(error_kecil->getPertinence()) + String(" -- ") +
                    String(error_sedang->getPertinence()) + String(" -- ") +
                    String(error_tinggi->getPertinence()));

    APP_DEBUG_PRINT(String("VOLFZ = ") + String(volx) + String(" -- ") +
                    String(vol_kosong->getPertinence()) + String(" -- ") +
                    String(vol_medium->getPertinence()) + String(" -- ") +
                    String(vol_penuh->getPertinence()));

    APP_DEBUG_PRINT(String("DURATION = ") +
                    String((uint32_t)((float)durx * 1000.0)));
  } else {
    APP_DEBUG_PRINT(F("INVALID VALUE"));
  }
}

/**
 * init relay gpio
 * @method relayInit
 */
void relayInit() {
  pinMode(PIN_RELAY, OUTPUT);
  digitalWrite(PIN_RELAY, LOW);
}

/**
 * all setup goes here
 * @method setup
 */
void setup() {
  APP_PORT_DEBUG.begin(9600);
  APP_DEBUG_PRINT(F("FUZZIFIER DHT22 INITIALIZE"));

  // init relay
  relayInit();

  // init fuzzy
  fuzzyInit();

  APP_DEBUG_PRINT(F("INIT DONE"));

  for (;;) {
    debugTest();
  }
}

/**
 * main loop
 * @method loop
 */
void loop() {
  // current time;

  // relay on or off
  processRelayOnOff();

  // time to process
  processFuzzySystem();

}
