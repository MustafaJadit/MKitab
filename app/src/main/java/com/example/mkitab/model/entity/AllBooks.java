package com.example.mkitab.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllBooks {

    @SerializedName("1")
    private List<_$1Bean> _$1;
    @SerializedName("2")
    private List<_$2Bean> _$2;
    private List<ShelvesBean> shelves;
    private List<SlidersBean> sliders;

    public List<_$1Bean> get_$1() {
        return _$1;
    }

    public void set_$1(List<_$1Bean> _$1) {
        this._$1 = _$1;
    }

    public List<_$2Bean> get_$2() {
        return _$2;
    }

    public void set_$2(List<_$2Bean> _$2) {
        this._$2 = _$2;
    }

    public List<ShelvesBean> getShelves() {
        return shelves;
    }

    public void setShelves(List<ShelvesBean> shelves) {
        this.shelves = shelves;
    }

    public List<SlidersBean> getSliders() {
        return sliders;
    }

    public void setSliders(List<SlidersBean> sliders) {
        this.sliders = sliders;
    }

    public static class _$1Bean {
        /**
         * id : 1
         * title : ئانا يۇرت رومانى
         * pic : http://www.ewlad.biz/awazliqkitap1/kitap_muqawa/anayurt.jpg
         * favorite : 0
         * ret : 0
         * cat : 1
         */

        private int id;
        private String title;
        private String pic;
        private int favorite;
        private int ret;
        private int cat;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getFavorite() {
            return favorite;
        }

        public void setFavorite(int favorite) {
            this.favorite = favorite;
        }

        public int getRet() {
            return ret;
        }

        public void setRet(int ret) {
            this.ret = ret;
        }

        public int getCat() {
            return cat;
        }

        public void setCat(int cat) {
            this.cat = cat;
        }
    }

    public static class _$2Bean {
        /**
         * id : 1
         * title : تارىخىي رومان
         */

        private int id;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class ShelvesBean {
        /**
         * id : 1
         * title : تەۋسىيىلەر
         */

        private int id;
        private String title;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class SlidersBean {
        /**
         * id : 3
         * title : ئېلان1
         * pic : http://172.104.143.75/awazliq_kitab/uploads/kitablar1.jpg
         * isURL : 1
         * URL : 1
         * Loc : 0
         */

        private int id;
        private String title;
        private String pic;
        private int isURL;
        private String URL;
        private int Loc;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getIsURL() {
            return isURL;
        }

        public void setIsURL(int isURL) {
            this.isURL = isURL;
        }

        public String getURL() {
            return URL;
        }

        public void setURL(String URL) {
            this.URL = URL;
        }

        public int getLoc() {
            return Loc;
        }

        public void setLoc(int Loc) {
            this.Loc = Loc;
        }
    }
}
