grammar Solution;

options {
	language = Java;
}

almanach1 returns [ASD.Almanach out]
	@init {
		ASD.Almanach asd = new ASD.Almanach();
	}
	: almanachMapping1[asd]* {
		$out = asd;
	}
	;

almanach2 returns [ASD.Almanach out]
	@init {
		ASD.Almanach asd = new ASD.Almanach();
	}
	: almanachMapping2[asd]* {
		$out = asd;
	}
	;

almanachMapping1 [ASD.Almanach in]
	: seeds[in]
	| seedToSoil[in]
	| soilToFertilizer[in]
	| fertilizerToWater[in]
	| waterToLight[in]
	| lightToTemperature[in]
	| temperatureToHumidity[in]
	| humidityToLocation[in]
  ;

almanachMapping2 [ASD.Almanach in]
	: seedsRange[in]
	| seedToSoil[in]
	| soilToFertilizer[in]
	| fertilizerToWater[in]
	| waterToLight[in]
	| lightToTemperature[in]
	| temperatureToHumidity[in]
	| humidityToLocation[in]
  ;

seedsRange [ASD.Almanach in]
	: SEEDS rangeList {
		in.setSeedRanges($rangeList.out);
	}
	;

seeds [ASD.Almanach in]
	: SEEDS seedList {
		in.setSeedRanges($seedList.out);
	}
	;

seedToSoil [ASD.Almanach in]
	: SEED_TO_SOIL mappingList {
		in.setSeedToSoil($mappingList.out);
	}
	;

soilToFertilizer [ASD.Almanach in]
	: SOIL_TO_FERTILIZER mappingList {
		in.setSoilToFertilizer($mappingList.out);
	}
	;

fertilizerToWater [ASD.Almanach in]
	: FERTILIZER_TO_WATER mappingList {
		in.setFertilizerToWater($mappingList.out);
	}
	;

waterToLight [ASD.Almanach in]
	: WATER_TO_LIGHT mappingList {
		in.setWaterToLight($mappingList.out);
	}
	;

lightToTemperature [ASD.Almanach in]
	: LIGHT_TO_TEMPERATURE mappingList {
		in.setLightToTemperature($mappingList.out);
	}
	;

temperatureToHumidity [ASD.Almanach in]
	: TEMPERATURE_TO_HUMIDITY mappingList {
    in.setTemperatureToHumidity($mappingList.out);
  }
	;

humidityToLocation [ASD.Almanach in]

	: HUMIDITY_TO_LOCATION mappingList {
		in.setHumidityToLocation($mappingList.out);
	}
	;

mappingList returns [List<ASD.Mapping> out]
	@init {
		List<ASD.Mapping> maps = new ArrayList<>();
	}
	: (map {
		maps.add($map.out);
	})* {
		$out = maps;
	}
	;

seedList returns [List<ASD.Range> out]
	@init {
		List<ASD.Range> ranges = new ArrayList<>();
	}
	: (seed=INT {
		ranges.add(new ASD.Range(Long.parseLong($seed.text), Long.parseLong($seed.text) + 1));
	})* {
		$out = ranges;
	}
	;

rangeList returns [List<ASD.Range> out]
	@init {
		List<ASD.Range> ranges = new ArrayList<>();
	}
	: (range {
		ranges.add($range.out);
	})* {
		$out = ranges;
	}
	;

range returns [ASD.Range out]
	: begin=INT length=INT {
		$out = new ASD.Range(Long.parseLong($begin.text), Long.parseLong($begin.text) + Long.parseLong($length.text));
	}
	;

map returns [ASD.Mapping out]
  : dest=INT source=INT length=INT {
		$out = new ASD.Mapping(Long.parseLong($dest.text), Long.parseLong($source.text), Long.parseLong($length.text));
	}
  ;

SEEDS : 'seeds' COLON;
SEED_TO_SOIL : 'seed-to-soil map' COLON;
SOIL_TO_FERTILIZER : 'soil-to-fertilizer map' COLON;
FERTILIZER_TO_WATER : 'fertilizer-to-water map' COLON;
WATER_TO_LIGHT : 'water-to-light map' COLON;
LIGHT_TO_TEMPERATURE : 'light-to-temperature map' COLON;
TEMPERATURE_TO_HUMIDITY : 'temperature-to-humidity map' COLON;
HUMIDITY_TO_LOCATION : 'humidity-to-location map' COLON;

INT
   // integer part forbis leading 0s (e.g. `01`)
   : '0' | [1-9][0-9]*
   ;

fragment COLON : ':';

WS
	 : [ \t\r\n]+ -> skip
	 ;
