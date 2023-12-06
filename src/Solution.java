class Solution {
    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        int endNodeIndex = n - 1;
        long distance = 0;
        int currentIndex = -1;
        while (!isDone(deliveries, pickups)) {
            // pickup check
            int freeCap = cap;
            int reversePickupOffset = 0;
            for (int i = endNodeIndex; i >= 0; i--) {
                freeCap -= pickups[i];
                if (freeCap > 0 && i != 0) {
                    reversePickupOffset++;
                } else {
                    break;
                }
            }
            // load
            int loaded = 0;
            for (int i = endNodeIndex; i >= endNodeIndex - reversePickupOffset; i--) {
                for (int j = 0; j < deliveries[i]; j++) {
                    if (loaded < cap) {
                        loaded++;
                    }
                }
            }
            // deliver and pickup
            int deliveryCount = loaded;
            for (int i = endNodeIndex; i >= endNodeIndex - reversePickupOffset; i--) {
                int count = deliveries[i];
                if (count > 0) {
                    if (currentIndex != i) {
                        distance += Math.abs(i - currentIndex);
                        currentIndex = i;
                    }
                    for (int j = 0; j < count; j++) {
                        if (loaded > 0 && deliveryCount > 0) {
                            deliveryCount--;
                            loaded--;
                            deliveries[i]--;
                        }
                    }
                }
                count = pickups[i];
                if (count > 0) {
                    if (currentIndex != i) {
                        distance += Math.abs(i - currentIndex);
                        currentIndex = i;
                    }
                    for (int j = 0; j < count; j++) {
                        if (loaded < cap) {
                            loaded++;
                            pickups[i]--;
                        }
                    }
                }
            }
            // go to warehouse
            distance += currentIndex + 1;
            currentIndex = -1;
        }

        return distance;
    }

    public boolean isDone(int[] deliveries, int[] pickups) {
        int sum = 0;
        for (int count : deliveries) {
            sum += count;
        }
        for (int count : pickups) {
            sum += count;
        }
        return sum == 0;
    }
}