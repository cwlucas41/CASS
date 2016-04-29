package cass.wsd;

/**
 * Enumeration representing implemented WSD algorithms.
 * @author cwlucas41
 *
 */
public enum Algorithm {
	LESK,
	LESK_WITH_FILTER,
	NEW_LESK,
	NEW_LESK_WITH_FILTER,
	STOCHASTIC_GRAPH,
	FREQUENCY,
	RANDOM,
	RANDOM_WITH_FILTER;
}
