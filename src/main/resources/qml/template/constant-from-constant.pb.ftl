node {
  name: "${nodeName}"
  op: "Const"
  attr {
    key: "dtype"
    value {
      type: ${dType}
    }
  }
  attr {
    key: "value"
    value {
      tensor {
        dtype: ${dType}
        tensor_shape {
        }
        ${dTypeArgumentName}: ${initialValue}
      }
    }
  }
}
