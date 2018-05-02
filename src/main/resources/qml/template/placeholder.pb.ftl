node {
  name: "${nodeName}"
  op: "Placeholder"
  attr {
    key: "dtype"
    value {
      type: ${dType}
    }
  }
  attr {
    key: "shape"
    value {
      shape {
        unknown_rank: true
      }
    }
  }
}