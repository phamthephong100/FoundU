package com.example.foundu.routing;

import java.util.ArrayList;

public interface RoutingListener {
    void onRoutingFailure(RouteException e);

    void onRoutingStart();

    void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex);

    void onRoutingCancelled();
}
