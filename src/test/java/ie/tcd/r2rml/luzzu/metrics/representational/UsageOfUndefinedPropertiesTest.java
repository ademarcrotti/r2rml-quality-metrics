package ie.tcd.r2rml.luzzu.metrics.representational;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.ModelFactory;
import org.junit.Before;
import org.junit.Test;

import io.github.luzzu.datatypes.r2rml.R2RMLMapping;
import io.github.luzzu.datatypes.r2rml.R2RMLMappingFactory;
import io.github.luzzu.exceptions.BeforeException;
import io.github.luzzu.exceptions.MetricProcessingException;
import io.github.luzzu.linkeddata.qualitymetrics.commons.TestLoader;

public class UsageOfUndefinedPropertiesTest {
	TestLoader loader = new TestLoader();
	private List<R2RMLMapping> mappings;
	UsageOfUndefinedProperties metric;
	String file1 = "undefined-properties/mapping.ttl";
	String file2 = "undefined-properties/mapping2.ttl";
	String file3 = "undefined-properties/mapping3.ttl";
	String file4 = "undefined-properties/mapping4.ttl";

	@Before
	public void before() {
		mappings = new ArrayList<R2RMLMapping>();
		metric = new UsageOfUndefinedProperties();
	}

	@Test
	public void undefinedPropetyReadingR2RMLMappingTest() throws BeforeException, MetricProcessingException {
		mappings.add(R2RMLMappingFactory.createR2RMLMappingFromModel(ModelFactory.createDefaultModel().read(file1)));
		Object[] args = new Object[] { mappings };
		metric.before(args);

		assertEquals(1, metric.getMappings().size());
	}

	@Test
	public void withOneUndefinedPropertyTest() throws BeforeException, MetricProcessingException {
		mappings.add(R2RMLMappingFactory.createR2RMLMappingFromModel(ModelFactory.createDefaultModel().read(file1)));
		Object[] args = new Object[] { mappings };
		metric.before(args);
		metric.compute(null);

		assertEquals(0d, metric.metricValue(), 0.00001);
	}

	@Test
	public void withOneDefinedAndOneUndefinedPropertyTest() throws BeforeException, MetricProcessingException {
		mappings.add(R2RMLMappingFactory.createR2RMLMappingFromModel(ModelFactory.createDefaultModel().read(file2)));
		Object[] args = new Object[] { mappings };
		metric.before(args);
		metric.compute(null);

		assertEquals(0.5d, metric.metricValue(), 0.00001);
	}

	@Test
	public void withTwoDefinedPropertiesTest() throws BeforeException, MetricProcessingException {
		mappings.add(R2RMLMappingFactory.createR2RMLMappingFromModel(ModelFactory.createDefaultModel().read(file3)));
		Object[] args = new Object[] { mappings };
		metric.before(args);
		metric.compute(null);

		assertEquals(1d, metric.metricValue(), 0.00001);
	}

	@Test
	public void withTwoDefinedPropertiesInTwoTriplesMapsTest() throws BeforeException, MetricProcessingException {
		mappings.add(R2RMLMappingFactory.createR2RMLMappingFromModel(ModelFactory.createDefaultModel().read(file4)));
		Object[] args = new Object[] { mappings };
		metric.before(args);
		metric.compute(null);

		assertEquals(0.5d, metric.metricValue(), 0.00001);
	}

}
