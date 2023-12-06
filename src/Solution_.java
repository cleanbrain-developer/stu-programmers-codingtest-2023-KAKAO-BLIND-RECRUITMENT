import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <pre>
 * User: clean_brain
 * Date: 2023-12-04
 * Comments:
 * </pre>
 */
@Deprecated
public class Solution_ {
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

        public int getCurrentSiteId() {
            return currentSiteId;
        }

        public int getDistance() {
            return distance;
        }

        public void loadDeliveries() {
            for (int i = this.sites.size() - 1; i > 0; i--) {
                Site site = this.sites.get(i);
                if (site.getDeliveryCount() > 0 || site.getPickupCount() > 0) {
                    if (site.getDeliveryCount() > 0) {
                        for (int j = 0; j < site.getDeliveryCount() && this.isLoadable(); j++) {
                            this.loadItem(new Item(true, i));
                        }
                    }
                }

                if (site.getDeliveryCount() > 0) {
                    for (int j = 0; j < site.getDeliveryCount() && this.isLoadable(); j++) {
                        this.loadItem(new Item(true, i));
                    }

                    if (this.totalCap < site.getPickupCount()) {
                        break;
                    } else if (this.totalCap < (site.getPickupCount() + getPrevSitePickupCount(site.getId()))) {
                        break;
                    }
                }

                // if (i > 2) {
                // 	Site prevSite = this.sites.get(i - 1);
                // 	this.loadedItems.size() + prevSite.getDeliveryCount()
                // }
            }
        }

        public int getPrevSiteDeliverCount(int siteId) {
            if (siteId > 1) {
                return this.sites.get(siteId - 1).getDeliveryCount();
            }
            return 0;
        }

        public int getPrevSitePickupCount(int siteId) {
            if (siteId > 1) {
                return this.sites.get(siteId - 1).getPickupCount();
            }
            return 0;
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
                this.goToSite(item.getSiteId());
                this.unloadItem(item.getSiteId());
                this.loadPickups(item.getSiteId());
            }

            int siteId = this.getLongestPickupSiteId();
            while (this.isLoadable() && siteId > 0) {
                this.goToSite(siteId);
                this.loadPickups(siteId);
                siteId--;
            }
        }

        public int getLongestPickupSiteId() {
            for (int i = this.sites.size() - 1; i > 0; i--) {
                Site site = this.sites.get(i);
                if (site.getPickupCount() > 0) {
                    return site.getId();
                }
            }
            return 0;
        }

        public void goToWarehouse() {
            this.goToSite(0);
            this.loadedItems = new LinkedList<>();
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

        public void goToSite(int siteId) {
            this.distance += Math.abs(this.currentSiteId - siteId);
            this.currentSiteId = siteId;
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

