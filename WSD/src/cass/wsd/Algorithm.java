package cass.wsd;

/**
 * Enumeration representing implemented WSD algorithms.
 * @author cwlucas41
 *
 */
public enum Algorithm {
	LESK,
	LESK_WITH_FILTER,
	BETTER_LESK,
	BETTER_LESK_WITH_FILTER,
	STOCHASTIC_GRAPH,
	FREQUENCY,
	RANDOM,
	RANDOM_WITH_FILTER;
}
