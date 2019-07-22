package cucumber.perf.api;

import gherkin.ast.ScenarioDefinition;

import java.util.ArrayList;
import java.util.List;

import cucumber.runtime.FeaturePathFeatureSupplier;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.RuntimeOptionsFactory;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.model.CucumberFeature;
import cucumber.runtime.model.FeatureLoader;

public class FeatureBuilder {

	public static RuntimeOptions createRuntimeOptions(Class<?> clazz)
	{
		RuntimeOptionsFactory runtimeOptionsFactory = new RuntimeOptionsFactory(clazz);
		return runtimeOptionsFactory.create();
	}
	
	public static RuntimeOptions createRuntimeOptions(List<String> args)
	{
		return new RuntimeOptions(args);
	}
	
	public static List<CucumberFeature> getFeatures(RuntimeOptions runtimeOptions) {
		ClassLoader classLoader = FeatureBuilder.class.getClassLoader();
		ResourceLoader resourceLoader = new MultiLoader(classLoader);
		FeatureLoader featureLoader = new FeatureLoader(resourceLoader);
		FeaturePathFeatureSupplier featureSupplier =  new FeaturePathFeatureSupplier(featureLoader, runtimeOptions);
		return featureSupplier.get();
	}
	
	public static List<CucumberFeature> FindFeatures(String prefix, List<CucumberFeature> features) {
		List<CucumberFeature> result = new ArrayList<CucumberFeature>();
		for (CucumberFeature f : features) {
			if (f.getGherkinFeature().getFeature().getName().toLowerCase().startsWith(prefix)) {
				result.add(f);
			}
		}
		return result;
	}

	public static List<ScenarioDefinition> FindScenarios(String prefix, String feature,
			List<CucumberFeature> features) {
		List<ScenarioDefinition> result = new ArrayList<ScenarioDefinition>();
		for (CucumberFeature f : features) {
			if (f.getGherkinFeature().getFeature().getName().equalsIgnoreCase(feature)) {
				for (ScenarioDefinition s : f.getGherkinFeature().getFeature().getChildren()) {
					if (s.getName().startsWith(prefix)) {
						result.add(s);
					}
				}
			}
		}
		return result;
	}

	public static List<List<ScenarioDefinition>> GetScenarios(List<CucumberFeature> features) {
		List<List<ScenarioDefinition>> result = new ArrayList<List<ScenarioDefinition>>();
		for (CucumberFeature f : features) {
			List<ScenarioDefinition> sc = new ArrayList<ScenarioDefinition>();
			for (ScenarioDefinition s : f.getGherkinFeature().getFeature().getChildren()) {
				sc.add(s);
			}
			result.add(sc);
		}
		return result;
	}

}
