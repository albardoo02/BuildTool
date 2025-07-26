package net.azisaba.buildtool.operation;

public interface Operation {

    void place();

    void subtract();

    enum OperationType {
        LONG_LENGTH_PLACE,
        SQUARE_BLOCK_PLACE;
    }
}
