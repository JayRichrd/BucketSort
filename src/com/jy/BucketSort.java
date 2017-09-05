package com.jy;

import java.util.Arrays;

public class BucketSort {

	public static void main(String[] args) {
		DataWrap[] dataWraps = new DataWrap[] { new DataWrap(9, ""), new DataWrap(5, ""), new DataWrap(-1, ""),
				new DataWrap(8, ""), new DataWrap(5, ""), new DataWrap(7, ""), new DataWrap(3, ""),
				new DataWrap(-3, ""), new DataWrap(1, ""), new DataWrap(3, "") };

		System.out.println("排序前：" + Arrays.toString(dataWraps));
		// 开始时间
		long startTime = System.currentTimeMillis();
		bucketSort(dataWraps);
		// 结束时间
		long stopTime = System.currentTimeMillis();
		System.out.println("=====================排序结束=====================");
		System.out.println("排序耗时t：" + (stopTime - startTime) + "ms");
		System.out.println("排序后（从小到大）：" + Arrays.toString(dataWraps));
	}

	/**
	 * 桶式排序
	 * 
	 * @param dataWraps
	 *            待排序的序列数组
	 */
	public static void bucketSort(DataWrap[] dataWraps) {
		System.out.println("=====================开始排序=====================");
		// 序列中的最大、最小值
		DataWrap max = dataWraps[0];
		DataWrap min = dataWraps[0];
		// 遍历序列求出最大、最小值
		for (DataWrap dataWrap : dataWraps) {
			if (max.compareTo(dataWrap) < 0)
				max = dataWrap;
			if (min.compareTo(dataWrap) > 0)
				min = dataWrap;
		}
		// 调用重载的桶式排序
		bucketSort(dataWraps, min.data, max.data);
	}

	/**
	 * 桶式排序
	 * 
	 * @param dataWraps
	 *            待排序的序列数组
	 * @param min
	 *            序列的最小值
	 * @param max
	 *            序列的最大值
	 */
	public static void bucketSort(DataWrap[] dataWraps, int min, int max) {
		System.out.println("序列的最小值min：" + min);
		System.out.println("序列的最大值max：" + max);

		// 序列的长度
		int arrayLength = dataWraps.length;
		// 定义作为桶的数组
		// 数组的大小为max-min+1
		int[] buckets = new int[max - min + 1];
		// 计算每个元素在序列中出现的次数
		for (int i = 0; i < arrayLength; i++)
			buckets[dataWraps[i].data - min]++;

		System.out.println("=====================每个元素在序列中出现的次数=====================");
		System.out.println(Arrays.toString(buckets));

		// 计算落入各个桶中的元素在序列中的位置
		// 根据buckets[i] = buckets[i-1] + buckets[i]计算新数组
		for (int i = 1; i < buckets.length; i++)
			buckets[i] = buckets[i - 1] + buckets[i];

		System.out.println("=====================落入各个桶中的元素在序列中的位置=====================");
		System.out.println(Arrays.toString(buckets));

		// 定义一个临时数组，并将源数组的原样复制过去
		DataWrap[] temp = new DataWrap[arrayLength];
		System.arraycopy(dataWraps, 0, temp, 0, arrayLength);

		// 根据buckets中的信息，将temp中的元素插入到源数组的中的合适的位置
		// 必须从temp的最后一个元素往前取出元素来安排位置，因为buckets中的元素是递减的，这样才能保证桶式排序是稳定的
		for (int i = arrayLength - 1; i >= 0; i--)
			// 考虑到序列中可能出现同一个数出现几次的情况，因此需要在取出buckets数组中某个位置的值后，需要递减
			// 桶中的数值时元素在数组中第几的位置，例如：1代表第1的位置，但最终在数组中的索引是0。所以这里先递减在赋值
			dataWraps[--buckets[temp[i].data - min]] = temp[i];
	}

}
