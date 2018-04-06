node {
  name: "${nodeName}/initial_value"
  op: "Const"
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
  attr {
    key: "dtype"
    value {
      type: ${dType}
    }
  }
}
node {
  name: "${nodeName}"
  op: "VariableV2"
  attr {
    key: "dtype"
    value {
      type: ${dType}
    }
  }
  attr {
    key: "container"
    value {
      s: ""
    }
  }
  attr {
    key: "shape"
    value {
      shape {
      }
    }
  }
  attr {
    key: "shared_name"
    value {
      s: ""
    }
  }
}
node {
  name: "${nodeName}/Assign"
  op: "Assign"
  input: "${nodeName}"
  input: "${nodeName}/initial_value"
  attr {
    key: "T"
    value {
      type: ${dType}
    }
  }
  attr {
    key: "_class"
    value {
      list {
        s: "loc:@${nodeName}"
      }
    }
  }
  attr {
    key: "validate_shape"
    value {
      b: true
    }
  }
  attr {
    key: "use_locking"
    value {
      b: true
    }
  }
}
node {
  name: "${nodeName}/read"
  op: "Identity"
  input: "${nodeName}"
  attr {
    key: "T"
    value {
      type: ${dType}
    }
  }
  attr {
    key: "_class"
    value {
      list {
        s: "loc:@${nodeName}"
      }
    }
  }
}
