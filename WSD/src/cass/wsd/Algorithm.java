package cass.wsd;

/**
 * Enumeration representing implemented WSD algorithms.
 * @author cwlucas41
 *
 */
public enum Algorithm {
	LESK,
	LESK_WITH_FILTER,
	CUSTOM_LESK,
	CUSTOM_LESK_WITH_FILTER,
	HYPERNYM_DISTANCE,
	HYPERNYM_DISTANCE_WITH_FILTER,
	RANDOM,
	RANDOM_WITH_FILTER,
	FREQUENCY;
}
