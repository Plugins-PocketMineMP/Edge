# Edge
A plugin for Nukkit

# API

You can change info's text by your plugin.

```java
import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;
import me.alvin0319.edge.edge.EdgeReplacer;

class MyCustomEdgeReplacer implements EdgeReplacer{

    public String getInfo(Player player){
        return "your custom info";
    }
}

class Main extends PluginBase{

    public void onEnable(){
        me.alvin0319.edge.Main.getInstance().setReplacer(new MyCustomEdgeReplacer());
    }
}
```

# ScreenShot
![](https://raw.githubusercontent.com/alvin0319/Edge/master/images/image.png)
