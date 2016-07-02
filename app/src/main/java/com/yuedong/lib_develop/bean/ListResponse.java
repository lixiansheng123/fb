package com.yuedong.lib_develop.bean;

import java.util.List;

public abstract class ListResponse<T> extends BaseResponse {
    public abstract List<T> getDataList();
    public abstract Data getData();


    public class Data{
        private int totalcount;
        private int totalpage;
        private int max;

        public int getTotalcount() {
            return totalcount;
        }

        public void setTotalcount(int totalcount) {
            this.totalcount = totalcount;
        }

        public int getTotalpage() {
            return totalpage;
        }

        public void setTotalpage(int totalpage) {
            this.totalpage = totalpage;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }


        @Override
        public String toString() {
            return "Data{" +
                    "totalcount=" + totalcount +
                    ", totalpage=" + totalpage +
                    ", max=" + max +
                    '}';
        }
    }

}
