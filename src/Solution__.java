import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Deprecated
class Solution__ {
    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        DeliveryCar deliveryCar = new DeliveryCar(cap, n, deliveries, pickups);
        while (!deliveryCar.isWorkDone(n)) {
            deliveryCar.loadDeliveries();
            deliveryCar.deliverAndPickup();
            deliveryCar.goToWarehouse();
        }
        return deliveryCar.getDistance();
    }

    private static class DeliveryCar {
        private int totalCap;
        private Queue<Item> loadedItems = new LinkedList<>();
        private List<Site> sites;
        private int currentSiteId = 0;
        private int finalStieIdPerDelivery = 0;
        private int distance = 0;

        public DeliveryCar(int totalCap, int n, int[] deliveries, int[] pickups) {
            this.totalCap = totalCap;
            List<Site> sites = new ArrayList<>();
            sites.add(new Site(0, 0, 0));
            for (int i = 0; i < n; i++) {
                sites.add(new Site(i + 1, deliveries[i], pickups[i]));
            }
            this.sites = sites;
        }

        public int getDistance() {
            return distance;
        }

        public void loadDeliveries() {
            int m = 0;
            int pickupSum = 0;
            int free = this.totalCap;
            // pickup count 확인
            for (int i = this.sites.size() - 1; i > 0; i--) {
                Site site = this.sites.get(i);
                pickupSum += site.getPickupCount();
                free -= pickupSum;
                if (free >= 0) {
                    m++;
                } else {
                    break;
                }
            }
            this.finalStieIdPerDelivery = this.sites.size() - 1 - m;

            // load delivery
            int l = 0;
            for (int i = this.sites.size() - 1; i > 0; i--) {
                Site site = this.sites.get(i);
                for (int j = 0; j < site.getDeliveryCount() && this.isLoadable() && m >= l; j++) {
                    this.loadItem(new Item(true, i));
                }
                l++;
            }
        }

        public void loadPickups(int siteId) {
            Site site = this.sites.get(siteId);
            if (site.getPickupCount() > 0) {
                while (this.isLoadable()) {
                    site.decreasePickupCount();
                    this.loadItem(new Item(false, site.getId()));
                }
            }
        }

        public void deliverAndPickup() {
            while (!this.isDeliveryDone()) {
                Item item = this.loadedItems.peek();
                if (item.getSiteId() < this.finalStieIdPerDelivery) {
                    int n = 1;
                    while (this.currentSiteId > this.finalStieIdPerDelivery) {
                        int siteId = this.currentSiteId - n++;
                        goToSite(siteId);
                        this.loadPickups(siteId);
                    }
                }
                this.goToSite(item.getSiteId());
                this.unloadItem(item.getSiteId());
                this.loadPickups(item.getSiteId());
            }
            int n = 1;
            while (this.currentSiteId > this.finalStieIdPerDelivery) {
                int siteId = this.currentSiteId - n++;
                goToSite(siteId);
                this.loadPickups(siteId);
            }
        }

        public void goToWarehouse() {
            this.goToSite(0);
            this.loadedItems = new LinkedList<>();
        }

        public void goToSite(int siteId) {
            this.distance += Math.abs(this.currentSiteId - siteId);
            this.currentSiteId = siteId;
        }

        public void loadItem(Item item) {
            this.loadedItems.offer(item);
        }

        public void unloadItem(int siteId) {
            while (this.loadedItems.peek() != null && this.loadedItems.peek().siteId == siteId) {
                Item item = this.loadedItems.poll();
                this.sites.get(item.siteId).decreaseDeliveryCount();
            }
        }

        public boolean isLoadable() {
            return this.getLoadableCount() > 0;
        }

        public int getLoadableCount() {
            return this.totalCap - this.loadedItems.size();
        }

        public boolean isDeliveryDone() {
            boolean isDeliveryDone = true;
            for (Item item : this.loadedItems) {
                if (item.isDelivery()) {
                    isDeliveryDone = false;
                }
            }
            return isDeliveryDone;
        }

        public boolean isWorkDone(int n) {
            int count = 0;
            for (Site site : sites) {
                if (site.getDeliveryCount() == 0 && site.getPickupCount() == 0) {
                    count++;
                }
            }
            return count == n;
        }
    }

    private static class Site {
        private int id;
        private int deliveryCount;
        private int pickupCount;

        public Site(int id, int deliveryCount, int pickupCount) {
            this.id = id;
            this.deliveryCount = deliveryCount;
            this.pickupCount = pickupCount;
        }

        public int getId() {
            return id;
        }

        public int getDeliveryCount() {
            return this.deliveryCount;
        }

        public int getPickupCount() {
            return this.pickupCount;
        }

        public void decreaseDeliveryCount() {
            this.deliveryCount--;
        }

        public void decreasePickupCount() {
            this.pickupCount--;
        }
    }

    private static class Item {
        private boolean isDelivery;
        private int siteId;

        public Item(boolean isDelivery, int siteId) {
            this.isDelivery = isDelivery;
            this.siteId = siteId;
        }

        public boolean isDelivery() {
            return isDelivery;
        }

        public int getSiteId() {
            return siteId;
        }
    }
}