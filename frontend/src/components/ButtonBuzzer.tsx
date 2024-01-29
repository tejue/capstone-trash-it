import styled from "styled-components";

type ButtonBuzzerProps = {
    handleClick: () => void,
    buttonText?: string,
    color?: string
    $position?: string;
}

export default function ButtonBuzzer(props: Readonly<ButtonBuzzerProps>) {
    return (
        <ButtonContainer $position={props.$position}>
            <BuzzerButton type={"button"} onClick={props.handleClick} color={props.color}>
                <StyledSpan color={props.color}>{props.buttonText}</StyledSpan>
            </BuzzerButton>
        </ButtonContainer>
    )
}

const ButtonContainer = styled.div<{$position?: string}>`
  display: flex;
  margin: 40px 30px;
  position: fixed;
  bottom: 0;
  right: ${({$position}) => $position === "right" ? 0 : undefined};
  left: ${({$position}) => $position === "left" ? 0 : undefined};
`

const BuzzerButton = styled.button<{color?: string}>`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100px;
  width: 100px;
  overflow: hidden;
  border-radius: 50%;
  border: 1px solid #1f1e1e;
  background: ${({color}) => color === "red" ? "linear-gradient(0deg, #560018, #9f0225)" : "linear-gradient(0deg, #013b01, #026002)"};
  box-shadow: 0 20px 30px rgba(0, 0, 0, 0.9);
  transition: box-shadow 0.2s;

  &:hover {
    box-shadow: 0 2px 5px rgb(0, 0, 0, 0.3);
  }
`

const StyledSpan = styled.span<{color?: string}>`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 80px;
  width: 80px;
  border: 1px solid ${({color}) => color === "red" ? "#810123" : "#015701"};
  border-radius: 50%;
  background: ${({color}) => color === "red" ? "linear-gradient(180deg, #560018, #9f0225)" : "linear-gradient(180deg, #013b01, #026002)"};
  font-size: 16px;
  color: #c0bdbd;
`