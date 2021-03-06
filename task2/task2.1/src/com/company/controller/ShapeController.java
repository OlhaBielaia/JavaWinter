package com.company.controller;

import com.company.model.ShapeModel;
import com.company.view.ShapeView;


public class ShapeController {
    private ShapeModel model = new ShapeModel();
    private ShapeView view = new ShapeView();

    public void run() {
        view.viewData(ConverterString.convertArraySahpes(model.getShapes()));
        view.viewData("--------------------------------------\n");
        view.viewData(ConverterString.convertDouble(model.areaAll(), "Total area"));
        InputType.InputShapeType(model,view);
        view.viewData("--------------------------------------\n");
        InputType.InputSortType(model,view);
    }

}
