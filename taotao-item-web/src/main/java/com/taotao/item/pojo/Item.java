package com.taotao.item.pojo;

import com.taotao.pojo.TbItem;

/**
 * Created by lee on 2017/3/22.
 */
@SuppressWarnings(value = "all")
public class Item extends TbItem {
    /**
     * 得到图片
     * @return
     */
    public String[] getImages() {
        String image = this.getImage();
        if (image != null && !"".equals(image)) {
            return image.split(",");
        }
        return null;
    }

    public Item() {
    }

    public Item(TbItem tbItem) {
        this.setId(tbItem.getId());
        this.setTitle(tbItem.getTitle());
        this.setSellPoint(tbItem.getSellPoint());
        this.setPrice(tbItem.getPrice());
        this.setNum(tbItem.getNum());
        this.setBarcode(tbItem.getBarcode());
        this.setImage(tbItem.getImage());
        this.setCid(tbItem.getCid());
        this.setStatus(tbItem.getStatus());
        this.setCreated(tbItem.getCreated());
        this.setUpdated(tbItem.getUpdated());
    }

}
